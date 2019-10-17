#
# WS SOAP 
# NOTIFICATION CENTER TIGO PY 
#
# REQUEST
#
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v1="http://www.tigo.com/SendNotificationRequest/V1" xmlns:v11="http://www.tigo.com/Core/Common/Header/Request/V1" xmlns:v12="http://www.tigo.com/SendNotification/V1">
   <soapenv:Header/>
   <soapenv:Body>
      <v1:SendNotificationRequest>
         <v11:RequestHeader>
            <!--You may enter the following 5 items in any order-->
            <v11:Consumer code="?" name="?">
               <!--Optional:-->
               <v11:Credentials>
                  <!--You have a CHOICE of the next 2 items at this level-->
                  <v11:certificates>?</v11:certificates>
                  <v11:User>
                     <v11:userName>?</v11:userName>
                     <v11:password>?</v11:password>
                  </v11:User>
               </v11:Credentials>
            </v11:Consumer>
            <v11:Transport code="?" name="?">
               <!--You may enter the following 5 items in any order-->
               <!--Optional:-->
               <v11:applicationCode>?</v11:applicationCode>
               <!--Optional:-->
               <v11:responseQueue>?</v11:responseQueue>
               <!--Optional:-->
               <v11:responseQueueAdministrator>?</v11:responseQueueAdministrator>
               <!--Optional:-->
               <v11:ServiceCode>?</v11:ServiceCode>
               <v11:communicationType>?</v11:communicationType>
            </v11:Transport>
            <v11:Service code="?" name="?">
               <!--You may enter the following 2 items in any order-->
               <!--Optional:-->
               <v11:retryCounter>?</v11:retryCounter>
               <!--Optional:-->
               <v11:retryInterval>?</v11:retryInterval>
            </v11:Service>
            <v11:Message messageId="?" messageIdCorrelation="?" conversationId="?">
               <!--You may enter the following 2 items in any order-->
               <!--Optional:-->
               <v11:timestamp>?</v11:timestamp>
               <!--Optional:-->
               <v11:expiration>?</v11:expiration>
            </v11:Message>
            <v11:Country name="?" isoCode="?"/>
         </v11:RequestHeader>
         <v1:requestBody>
            <v1:destiny>?</v1:destiny>
            <v1:idPlatform>?</v1:idPlatform>
            <v1:idProcess>?</v1:idProcess>
            <v1:parameters>
               <!--Zero or more repetitions:-->
               <v12:ParamType>
                  <v12:name>?</v12:name>
                  <v12:value>?</v12:value>
               </v12:ParamType>
            </v1:parameters>
            <v1:attachments>
               <!--Zero or more repetitions:-->
               <v12:AttachmentType>
                  <v12:name>?</v12:name>
                  <v12:content>cid:650731371907</v12:content>
                  <v12:mimeType>?</v12:mimeType>
               </v12:AttachmentType>
            </v1:attachments>
         </v1:requestBody>
      </v1:SendNotificationRequest>
   </soapenv:Body>
</soapenv:Envelope>

#
# EJEMPLO
#
# REQUEST 
#
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:v1="http://www.tigo.com/SendNotificationRequest/V1" xmlns:v11="http://www.tigo.com/Core/Common/Header/Request/V1" xmlns:v12="http://www.tigo.com/SendNotification/V1">
   <soapenv:Header/>
   <soapenv:Body>
      <v1:SendNotificationRequest>
         <v11:RequestHeader>
            <v11:Message messageId="1" messageIdCorrelation="1" conversationId="1">
            </v11:Message>
         </v11:RequestHeader>
         <v1:requestBody>
            <v1:destiny>0981407441</v1:destiny>
            <v1:idPlatform>14</v1:idPlatform>
            <v1:idProcess>6</v1:idProcess>
            <v1:parameters>
               <v12:ParamType>
                  <v12:name>mensaje</v12:name>
                  <v12:value>test</v12:value>
               </v12:ParamType>
            </v1:parameters>
         </v1:requestBody>
      </v1:SendNotificationRequest>
   </soapenv:Body>
</soapenv:Envelope>

# SUCCESS RESPONSE
#
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
      <ns0:SendNotificationResponse xmlns:ns0="http://www.tigo.com/SendNotificationResponse/V1">
         <ns2:ResponseHeader xmlns:ns2="http://www.tigo.com/Core/Common/Header/Response/V1">
            <ns2:Consumer code="" name=""/>
            <ns2:Service code="" name=""/>
            <ns2:Message messageId="1" messageIdCorrelation="1" conversationId="1">
               <ns2:state>OK</ns2:state>
            </ns2:Message>
            <ns2:Country name="" isoCode=""/>
         </ns2:ResponseHeader>
         <ns0:responseBody>
            <ns0:success>true</ns0:success>
            <ns0:destiny>0981407441</ns0:destiny>
            <ns0:transactionCode>0</ns0:transactionCode>
         </ns0:responseBody>
      </ns0:SendNotificationResponse>
   </soap:Body>
</soapenv:Envelope>


# ERROR RESPONSE
#
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
      <v1:SendNotificationResponse xmlns:v1="http://www.tigo.com/SendNotificationResponse/V1">
         <ns0:ResponseHeader xmlns:ns0="http://www.tigo.com/Core/Common/Header/Response/V1">
            <ns0:Consumer code="" name=""/>
            <ns0:Service code="" name=""/>
            <ns0:Message messageId="1" messageIdCorrelation="1" conversationId="1">
               <ns0:state>ERROR</ns0:state>
            </ns0:Message>
            <ns0:Country name="" isoCode=""/>
         </ns0:ResponseHeader>
         <v1:responseBody>
            <ns0:Error xmlns:ns0="http://www.tigo.com/Core/Common/Error/V1">
               <ns0:errorType>NEG</ns0:errorType>
               <ns0:code>ERR</ns0:code>
               <ns0:reason>Proceso no encontrado</ns0:reason>
               <ns0:description>Proceso no encontrado</ns0:description>
            </ns0:Error>
         </v1:responseBody>
      </v1:SendNotificationResponse>
   </soap:Body>
</soapenv:Envelope>


