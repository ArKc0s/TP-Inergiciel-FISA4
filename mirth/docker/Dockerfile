FROM eclipse-temurin:17-jdk

RUN apt-get clean && apt-get update && apt-get install -y --no-install-recommends locales make perl build-essential libz-dev \
unzip \ 
&& sed -i 's/^# *\(en_US.UTF-8\)/\1/' /etc/locale.gen && locale-gen

#Remove the installed version of openssl, and replace it with an up-to-date version
ARG OPENSSL_VERSION="openssl-3.1.4"
RUN apt-get -y remove openssl \
&& cd home \
&& curl -O https://www.openssl.org/source/${OPENSSL_VERSION}.tar.gz \
&& tar -xzvf ${OPENSSL_VERSION}.tar.gz \
&& cd ${OPENSSL_VERSION} \
&& ./config \
&& make install \
&& cd .. \
&& rm -rf ${OPENSSL_VERSION} \
&& rm -f ${OPENSSL_VERSION}.tar.gz

ENV LD_LIBRARY_PATH "/usr/local/lib64"

ARG ARTIFACT

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

RUN curl -SL https://s3.amazonaws.com/downloads.mirthcorp.com/connect/4.5.0.b3012/mirthconnect-4.5.0.b3012-unix.tar.gz \
    | tar -xzC /opt \
    && mv "/opt/Mirth Connect" /opt/connect

RUN useradd -u 1000 mirth
RUN mkdir -p /opt/connect/appdata && chown -R mirth:mirth /opt/connect/appdata
RUN mkdir -p /opt/hl7 && chown -R mirth:mirth /opt/hl7

#volume des partage pour echange FileSystem
VOLUME /opt/connect/appdata
VOLUME /opt/hl7
VOLUME /opt/connect/custom-extensions

WORKDIR /opt/connect

RUN rm -rf cli-lib manager-lib \
    && rm mirth-cli-launcher.jar mirth-manager-launcher.jar mccommand mcmanager
RUN (cat mcserver.vmoptions /opt/connect/docs/mcservice-java9+.vmoptions ; echo "") > mcserver_base.vmoptions
EXPOSE 8443

COPY ./entrypoint.sh /
RUN chmod 755 /entrypoint.sh

RUN chown -R mirth:mirth /opt/connect
USER mirth

ENTRYPOINT [ "/entrypoint.sh" ]
CMD ["./mcserver"]