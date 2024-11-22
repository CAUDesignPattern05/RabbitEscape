FROM ubuntu:focal

# 비대화식 설치를 위한 환경 변수 설정
ENV DEBIAN_FRONTEND=noninteractive

# https://github.com/andybalaam/rabbit-escape/blob/master/INSTALL.md
RUN apt-get update && apt-get install -y \
  git \
  make \
  openjdk-8-jdk \
  imagemagick \
  sox \
  inkscape \
  expect \
  grep \
  sed \
  rename \
  python3-lxml && apt clean

# 시간대 설정
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
  echo "Asia/Seoul" > /etc/timezone && \
  dpkg-reconfigure -f noninteractive tzdata

# develop bind-mount directory
WORKDIR /app/rabbit-escape

CMD ["make", "dist-swing"]