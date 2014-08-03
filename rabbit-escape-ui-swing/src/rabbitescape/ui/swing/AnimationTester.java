package rabbitescape.ui.swing;

import rabbitescape.engine.config.Config;
import rabbitescape.engine.config.ConfigTools;
import rabbitescape.engine.util.RealFileSystem;
import rabbitescape.render.AnimationCache;
import rabbitescape.render.AnimationLoader;
import rabbitescape.render.BitmapCache;
import rabbitescape.render.Renderer;
import rabbitescape.render.Sprite;

import javax.swing.*;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.*;

import static rabbitescape.engine.i18n.Translation.t;
import static rabbitescape.render.AnimationLoader.*;

public class AnimationTester extends JFrame
{
    private static final long serialVersionUID = 1L;

    public static final String CONFIG_PATH =
        "~/.rabbitescape/config/animationtester.properties"
            .replace( "~", System.getProperty( "user.home" ) );

    public static final String CFG_AT_WINDOW_LEFT =
        "animationtester.window.left";

    public static final String CFG_AT_WINDOW_TOP =
        "animationtester.window.top";

    private static final String CFG_AT_TILE_SIZE = "animationtester.tile.size";

    private static final String CFG_AT_ANIMATIONS =
        "animationtester.animations";

    private static final String[][] defaultAnimationNames = new String[][] {
        new String[] { NONE, NONE, NONE },
        new String[] { NONE, NONE, NONE },
        new String[] { NONE, NONE, NONE },

        new String[] { "rabbit_walking_right", NONE, NONE },
        new String[] { NONE, "rabbit_bashing_right", "rabbit_walking_right" },
        new String[] { NONE, NONE, NONE },

        new String[] { NONE, NONE, NONE },
        new String[] { NONE, NONE, NONE },
        new String[] { NONE, NONE, NONE }
    };

    private class Listener implements
        java.awt.event.MouseListener, ComponentListener
    {
        @Override
        public void mouseClicked( MouseEvent mouseEvent )
        {
            int i = screen2index( mouseEvent.getX(), mouseEvent.getY() );

            String[] possibilties = animationCache.listAll();

            JPanel threeDropDowns = new JPanel();
            JList<String> list0 = addAnimationList(
                threeDropDowns, possibilties, animationNames[i][0] );

            JList<String> list1 = addAnimationList(
                threeDropDowns, possibilties, animationNames[i][1] );

            JList<String> list2 = addAnimationList(
                threeDropDowns, possibilties, animationNames[i][2] );

            int retVal = JOptionPane.showOptionDialog(
                AnimationTester.this,
                threeDropDowns,
                "Change animation",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null
            );

            if ( retVal == JOptionPane.CANCEL_OPTION )
            {
                return;
            }

            animationNames[i][0] = noneForNull( list0.getSelectedValue() );
            animationNames[i][1] = noneForNull( list1.getSelectedValue() );
            animationNames[i][2] = noneForNull( list2.getSelectedValue() );

            saveAnimationsToConfig();
            loadBitmaps();
        }

        private void saveAnimationsToConfig()
        {
            atConfig.set(
                CFG_AT_ANIMATIONS, animationsToConfigString( animationNames ) );

            atConfig.save();
        }

        private String noneForNull( String value )
        {
            if ( value == null )
            {
                return NONE;
            }
            else
            {
                return value;
            }
        }

        private JList<String> addAnimationList(
            JPanel parent, String[] possibilities, String animation )
        {
            JList<String> list = new JList<String>( possibilities );

            parent.add( list );

            list.setSelectedValue( animation, true );

            return list;
        }

        @Override
        public void mousePressed( MouseEvent mouseEvent )
        {
        }

        @Override
        public void mouseReleased( MouseEvent mouseEvent )
        {
        }

        @Override
        public void mouseEntered( MouseEvent mouseEvent )
        {
        }

        @Override
        public void mouseExited( MouseEvent mouseEvent )
        {
        }

        @Override
        public void componentHidden( ComponentEvent arg0 )
        {
        }

        @Override
        public void componentMoved( ComponentEvent arg0 )
        {
            ConfigTools.setInt( atConfig, CFG_AT_WINDOW_LEFT, getX() );
            ConfigTools.setInt( atConfig, CFG_AT_WINDOW_TOP,  getY() );
            atConfig.save();
        }

        @Override
        public void componentResized( ComponentEvent arg0 )
        {
        }

        @Override
        public void componentShown( ComponentEvent arg0 )
        {
        }
    }

    private final int tileSize;
    private final int numTilesX;
    boolean running;

    private final java.awt.Canvas canvas;
    private final Config atConfig;
    private SwingBitmapScaler scaler;
    private SwingPaint paint;
    private SwingAnimation[][] frames;
    private final AnimationCache animationCache;

    private final String[][] animationNames;

    private static class InitUi implements Runnable
    {
        public AnimationTester animationTester;

        @Override
        public void run() {
            try
            {
                animationTester = new AnimationTester( createConfig() );
                synchronized ( this )
                {
                    notifyAll();
                }
            }
            catch ( Throwable t )
            {
                synchronized ( this )
                {
                    notifyAll();
                }
                t.printStackTrace();
                System.exit( 3 );
            }
        }

        public synchronized void loopWhenReady()
        {
            try
            {
                wait();
            }
            catch ( InterruptedException e )
            {
                // Will come when notified
            }

            if ( animationTester != null )
            {
                animationTester.loop();
            }
        }
    }

    public AnimationTester( Config atConfig )
    {
        this.atConfig = atConfig;
        this.tileSize = ConfigTools.getInt( atConfig, CFG_AT_TILE_SIZE );
        this.numTilesX = 3;
        this.animationCache = new AnimationCache( new AnimationLoader() );

        this.animationNames = animationsFromConfig(
            atConfig.get( CFG_AT_ANIMATIONS ) );

        int numTilesY = 3;

        setIgnoreRepaint( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        this.canvas = new java.awt.Canvas();
        canvas.setIgnoreRepaint( true );
        canvas.setPreferredSize(
            new java.awt.Dimension(
                tileSize * numTilesX, tileSize * numTilesY
            )
        );

        getContentPane().add( canvas, java.awt.BorderLayout.CENTER );

        loadBitmaps();

        Listener listener = new Listener();
        canvas.addMouseListener( listener );
        addComponentListener( listener );

        setBoundsFromConfig();

        setTitle( t( "Animation Tester" ) );
        pack();
        setVisible( true );

        // Must do this after frame is made visible
        canvas.createBufferStrategy( 2 );
    }

    private void setBoundsFromConfig()
    {
        int x = ConfigTools.getInt( atConfig, CFG_AT_WINDOW_LEFT );
        int y = ConfigTools.getInt( atConfig, CFG_AT_WINDOW_TOP );

        if ( x != Integer.MIN_VALUE && y != Integer.MIN_VALUE )
        {
            setLocation( x, y );
        }
    }

    private void loadBitmaps()
    {
        scaler = new SwingBitmapScaler();
        paint = new SwingPaint();
        SwingBitmapLoader bitmapLoader = new SwingBitmapLoader();

        BitmapCache<SwingBitmap> bitmapCache = new BitmapCache<SwingBitmap>(
            bitmapLoader, 500 );

        frames = loadAllFrames( bitmapCache, animationNames );
    }

    private SwingAnimation[][] loadAllFrames(
        BitmapCache<SwingBitmap> bitmapCache, String[][] animationNames )
    {
        SwingAnimation[][] ret =
            new SwingAnimation[animationNames.length][];

        int i = 0;
        for ( String[] animationTriplet : animationNames )
        {
            ret[i] = new SwingAnimation[animationTriplet.length];
            int j = 0;
            for ( String animationName : animationTriplet )
            {
                if ( !animationName.equals( NONE ) )
                {
                    try
                    {
                        ret[i][j] = new SwingAnimation(
                            bitmapCache,
                            animationCache.get( animationName )
                        );
                    }
                    catch( AnimationNotFound e )
                    {
                        e.printStackTrace();
                        ret[i][j] = null;
                    }
                }
                ++j;
            }
            ++i;
        }
        return ret;
    }

    public static void main( String[] args )
    {
        try
        {
            InitUi initUi = new InitUi();
            SwingUtilities.invokeLater( initUi );
            initUi.loopWhenReady();
        }
        catch( Throwable t )
        {
            t.printStackTrace();
            System.exit( 2 );
        }
    }

    private void loop()
    {
        BufferStrategy strategy = canvas.getBufferStrategy();

        Renderer renderer = new Renderer( 0, 0, tileSize );

        int frameSetNum = 0;
        int frameNum = 0;
        running = true;
        while( running && this.isVisible() )
        {
            new DrawFrame( strategy, renderer, frameSetNum, frameNum ).run();

            pause();

            ++frameNum;
            if ( frameNum == 10 )
            {
                frameNum = 0;
                ++frameSetNum;
                if ( frameSetNum == 3)
                {
                    frameSetNum = 0;
                }
            }
        }
    }

    private class DrawFrame extends BufferedDraw
    {
        private final int frameSetNum;
        private final int frameNum;
        private final Renderer renderer;

        public DrawFrame(
            BufferStrategy strategy,
            Renderer renderer,
            int frameSetNum,
            int frameNum
        )
        {
            super( strategy );
            this.renderer = renderer;
            this.frameSetNum = frameSetNum;
            this.frameNum = frameNum;
        }

        @Override
        void draw( java.awt.Graphics2D g )
        {
            g.setPaint( java.awt.Color.WHITE );
            g.fillRect(
                0,
                0,
                canvas.getWidth(),
                canvas.getHeight()
            );

            List<Sprite> sprites = new ArrayList<>();
            int i = 0;
            for ( SwingAnimation[] bmpSets : frames )
            {
                SwingAnimation bmps = bmpSets[frameSetNum];
                if ( bmps == null )
                {
                    ++i;
                    continue;
                }
                java.awt.Point loc = int2dim( i );
                SwingBitmapAndOffset bmp = bmps.get( frameNum );
                Sprite sprite = new Sprite(
                    bmp.bitmap,
                    scaler,
                    loc.x,
                    loc.y,
                    32,
                    bmp.offsetX,
                    bmp.offsetY
                );

                sprites.add( sprite );
                ++i;
            }

            renderer.render(
                new SwingCanvas( g ),
                    sprites.toArray( new Sprite[sprites.size() ] ),
                paint
            );
        }
    }

    private java.awt.Point int2dim( int i )
    {
        return new java.awt.Point( i % 3, i / 3 );
    }

    private int screen2index( int x, int y )
    {
        return ( numTilesX * ( y / tileSize ) ) + ( x / tileSize );
    }

    private void pause()
    {
        try
        {
            Thread.sleep( 100 );
        }
        catch (InterruptedException e)
        {
            // Ignore
        }
    }

    private static Config createConfig()
    {
        Config.Definition definition = new Config.Definition();

        definition.set(
            CFG_AT_WINDOW_LEFT,
            String.valueOf( Integer.MIN_VALUE ),
            "The x position of the animation tester window on the screen"
        );

        definition.set(
            CFG_AT_WINDOW_TOP,
            String.valueOf( Integer.MIN_VALUE ),
            "The y position of the animation tester window on the screen"
        );

        definition.set(
            CFG_AT_TILE_SIZE,
            String.valueOf( 128 ),
            "The on-screen size of tiles in the animation tester in pixels"
        );

        definition.set(
            CFG_AT_ANIMATIONS,
            "",
            "The animations selected to play in the animation tester"
            + " (3 per tile)"
        );

        return new Config( definition, new RealFileSystem(), CONFIG_PATH );
    }

    private static String animationsToConfigString( String[][] animations )
    {
        StringBuilder ret = new StringBuilder();

        for ( String[] arr : animations )
        {
            for ( String anim : arr )
            {
                ret.append( anim );
                ret.append( ' ' );
            }
        }

        return ret.toString();
    }

    private static String[][] animationsFromConfig( String cfgEntry )
    {
        if ( cfgEntry.isEmpty() )
        {
            return defaultAnimationNames;
        }

        String[] items = cfgEntry.split( " " );

        if ( items.length % 3 != 0 )
        {
            return defaultAnimationNames;
        }

        String[][] ret = new String[items.length / 3][];

        for ( int i = 0; i < items.length / 3; ++i )
        {
            ret[i] = new String[3];
            ret[i][0] = items[ i * 3 ];
            ret[i][1] = items[ i * 3 + 1 ];
            ret[i][2] = items[ i * 3 + 2 ];
        }

        return ret;
    }

}
