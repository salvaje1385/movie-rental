define([
    "dojo/_base/declare",
    "dojo/request/xhr",
],function(
    declare, 
    xhr,
) {
    return declare([], {

        /**
         * Call a backend service
         * @param params - The xhr method params
         * @param httpMethod - The HTTP method
         * @param responseCallBack - The response callback function
         * @param responseOnError - The error callback function
         */
        callService: function(
            params,
            httpMethod,
            responseCallBack,
            responseOnError
        ) {
            let xhrCall;

            params.method = httpMethod;
            params.timeout = 1200000;
            params.handleAs = "json";
            params.headers = {
                "Content-Type": "application/json; charset=utf-8",
                "Accept": "application/json",
            };
            params.preventCache = true;

            switch (httpMethod.toUpperCase()) {
                case "PUT":
                    xhrCall = xhr.put;
                    break;
                case "POST":
                    xhrCall = xhr.post;
                    break;
                case "DELETE":
                    xhrCall = xhr.del;
                    break;
                default:
                    // get
                    xhrCall = xhr.get;
                    break;
            }

            xhrCall(params.url, params).then(responseCallBack, responseOnError);
        },

    });
});
//# sourceURL=Methods.js
