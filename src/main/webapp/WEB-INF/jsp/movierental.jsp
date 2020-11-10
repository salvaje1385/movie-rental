<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Movies</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="https://ajax.googleapis.com/ajax/libs/dojo/1.10.4/dijit/themes/nihilo/nihilo.css"> 
        <script src="//ajax.googleapis.com/ajax/libs/dojo/1.14.1/dojo/dojo.js" data-dojo-config="isDebug: 1, async: 1, parseOnLoad: 1"></script>
        <link rel="stylesheet" href="/css/custom.css">

        <script>
          // See the using Custom Modules with a CDN Tutorial for more
          // information on configuring Dojo for CDN usage
          var dojoConfig = {
            async: true,
            dojoBlankHtmlUrl: location.pathname.replace(/\/[^/]+$/, "") + "/blank.html",
            packages: [{
              name: "movie-rental",
              location: location.pathname.replace(/\/[^/]+$/, "") + "/"
            }]
          };
        </script>

        <script>
          require([
            "dojo/parser",
            "/js/movie/Movies.js",
          ], function(parser) {
            // Invoke the dojo/parser
            parser.parse();
          });
        </script>

    </head>
    <body class="nihilo">
        <div id="page">
            <div data-dojo-type="/js/movie/Movies.js"></div>
        </div>
    </body>
</html>