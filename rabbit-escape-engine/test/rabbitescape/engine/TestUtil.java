package rabbitescape.engine;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.fail;
import static rabbitescape.engine.util.Util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestUtil
{
    @Test
    public void Assert_does_not_throw_when_true()
    {
        reAssert( true );
    }

    @Test( expected = AssertionError.class )
    public void Assert_throws_when_false()
    {
        reAssert( false );
    }

    @Test
    public void Join_returns_empty_for_no_items()
    {
        assertThat( join( "foo", new String[] {} ), equalTo( "" ) );
    }

    @Test
    public void Join_returns_item_for_1_item()
    {
        assertThat( join( "foo", new String[] { "xy" } ), equalTo( "xy" ) );
    }

    @Test
    public void Join_returns_concated_when_glue_is_empty()
    {
        assertThat( join( "", new String[] { "x", "y" } ), equalTo( "xy" ) );
    }

    @Test
    public void Join_sticks_items_together_with_glue()
    {
        assertThat(
            join( "::", new String[] { "x", "yz", "a" } ),
            equalTo( "x::yz::a" )
        );
    }

    @Test
    public void Empty_iterable_builds_into_empty_list()
    {
        Iterable<String> input = Arrays.asList( new String[] {} );

        assertThat(
            list( input ).toArray( new String[] {} ),
            equalTo( new String[] {} )
        );
    }

    @Test
    public void Build_list_from_iterable_with_list()
    {
        Iterable<String> input = Arrays.asList(
            new String[] { "a", "c", "b" } );

        assertThat(
            list( input ).toArray( new String[] {} ),
            equalTo( new String[] { "a", "c", "b" } )
        );
    }

    @Test
    public void Get_the_first_item_with_getNth()
    {
        assertThat(
            getNth( Arrays.asList( new String[] { "a", "b", "c" } ), 0 ),
            equalTo( "a" )
        );
    }

    @Test
    public void Get_the_last_item_with_getNth()
    {
        assertThat(
            getNth( Arrays.asList( new String[] { "a", "b", "c" } ), 2 ),
            equalTo( "c" )
        );
    }

    @Test
    public void Get_any_item_with_getNth()
    {
        assertThat(
            getNth( Arrays.asList( new String[] { "a", "b", "c" } ), 1 ),
            equalTo( "b" )
        );
    }

    @Test( expected = ArrayIndexOutOfBoundsException.class )
    public void Negative_n_for_getNth_is_an_error()
    {
        getNth( Arrays.asList( new String[] { "a", "b", "c" } ), -1 );
    }

    @Test( expected = ArrayIndexOutOfBoundsException.class )
    public void Past_end_of_list_for_getNth_is_an_error()
    {
        getNth( Arrays.asList( new String[] { "a", "b", "c" } ), 3 );
    }

    @Test
    public void Iterate_through_string_with_asChars()
    {
        assertThat(
            list( asChars( "acb" ) ).toArray( new Character[] {} ),
            equalTo( new Character[] { 'a', 'c', 'b' } )
        );
    }

    @Test
    public void Iterate_through_empty_string_with_asChars()
    {
        assertThat(
            list( asChars( "" ) ).toArray( new Character[] {} ),
            equalTo( new Character[] {} )
        );
    }

    @Test
    public void Empty_range_does_not_enter_loop()
    {
        for( int i : range( 0 ) )
        {
            fail( "Should not get here " + i );
        }
    }

    @Test
    public void Range_provides_consecutive_integer_values()
    {
        List<Integer> result = new ArrayList<Integer>();

        for( int i : range( 7 ) )
        {
            result.add( i );
        }

        assertThat(
            result.toArray( new Integer[] {} ),
            equalTo( new Integer[] { 0, 1, 2, 3, 4, 5, 6 } )
        );
    }

    @Test
    public void Chain_deals_with_initial_empty_list()
    {
        List<Integer> list1 = Arrays.asList( new Integer[] {} );
        List<Integer> list2 = Arrays.asList( new Integer[] { 6, 7, 8 } );

        List<Integer> result = new ArrayList<Integer>();

        for( int i : chain( list1, list2 ) )
        {
            result.add( i );
        }

        assertThat(
            result.toArray( new Integer[] {} ),
            equalTo( new Integer[] { 6, 7, 8 } )
        );
    }

    @Test
    public void Chain_deals_with_second_list_empty()
    {
        List<Integer> list1 = Arrays.asList( new Integer[] { 5, 4, 3 } );
        List<Integer> list2 = Arrays.asList( new Integer[] {} );

        List<Integer> result = new ArrayList<Integer>();

        for( int i : chain( list1, list2 ) )
        {
            result.add( i );
        }

        assertThat(
            result.toArray( new Integer[] {} ),
            equalTo( new Integer[] { 5, 4, 3 } )
        );
    }

    @Test
    public void Chain_deals_with_both_lists_empty()
    {
        List<Integer> list1 = Arrays.asList( new Integer[] {} );
        List<Integer> list2 = Arrays.asList( new Integer[] {} );

        List<Integer> result = new ArrayList<Integer>();

        for( int i : chain( list1, list2 ) )
        {
            result.add( i );
        }

        assertThat(
            result.toArray( new Integer[] {} ),
            equalTo( new Integer[] {} )
        );
    }

    @Test
    public void Chain_concatenates_two_iterables()
    {
        List<Integer> list1 = Arrays.asList( new Integer[] { 5, 4, 3 } );
        List<Integer> list2 = Arrays.asList( new Integer[] { 6, 7, 8 } );

        List<Integer> result = new ArrayList<Integer>();

        for( int i : chain( list1, list2 ) )
        {
            result.add( i );
        }

        assertThat(
            result.toArray( new Integer[] {} ),
            equalTo( new Integer[] { 5, 4, 3, 6, 7, 8 } )
        );
    }
}