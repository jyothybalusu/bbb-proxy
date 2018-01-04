FROM openjdk:8-jre-alpine
RUN mkdir -p /proxy/config
ADD target/* /proxy/
ADD proxyserver.sh /proxy/
ADD config/* /proxy/config/
WORKDIR /proxy
CMD /proxy/proxyserver.sh
EXPOSE 8081

