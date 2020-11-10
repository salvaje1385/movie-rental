define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dojo/dom-construct",
    "dojo/_base/lang",
    "dojo/on",
    "/js/movierental/Methods.js",
    "/js/movie/AddMovie.js",
    "dojo/text!./templates/Movies.html",
    "dijit/form/Button",
    "dijit/Dialog",
],function(
    declare, 
    _WidgetBase, 
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    domConstruct,
    lang,
    on,
    Methods,
    AddMovie,
    template,
    Button,
) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {

        // set our template
        templateString: template,

        _moviesServiceUrl: "movies",

        _methods: new Methods(),

        _addMovie: new AddMovie(),

        _movieData: null,

        _movieToDelete: null,

        postCreate: function() {
            this._getMovies();
        },

        /**
         * Get the movies from the service and build the movie table
         * @private
         */
        _getMovies: function() {
            this._movieToDelete = null;

            this._serviceCall(true);
        },

        /**
         * Call the service
         * @param {Boolean} callingGetService - If we're calling the GET service,
         * if this value is false, then we call the DELETE service
         * @private
         */
        _serviceCall: function(callingGetService) {
            let serviceUrl = this._moviesServiceUrl,
                httpMethod = "GET";

            if (!callingGetService) {
                serviceUrl = serviceUrl + "/" + this._movieToDelete.id;
                httpMethod = "DELETE";
            }

            let params = {
                    url: serviceUrl
                },
                responseOnSuccess = lang.hitch(this, function(response) {
                    this._movieToDelete = null;

                    if (callingGetService) {
                        // Create the movie table with the data from the service
                        this._createMovieTable(response);
                    } else {
                        location.reload(true);
                    }
                }),
                responseOnError = lang.hitch(this, function(error) {
                    console.log("ERROR! movies: _serviceCall:", error);
                });

            // Call the service
            this._methods.callService(
                params,
                httpMethod,
                responseOnSuccess,
                responseOnError
            );
        },

        /**
         * Create the movie table
         * @param response - The response data from the service
         * @private
         */
        _createMovieTable: function(response) {
            let movieProperties = ["title", "description", "stock", "rentalPrice", "salePrice"];

            this._movieData = response.content;

            // Create the movie table
            response.content.map(movie => {
                let tr = domConstruct.create("tr", null, this.moviesTable);

                // Create the columns for every movie property
                movieProperties.map(movieProperty => {
                    let td = domConstruct.create("td", null, tr);
                    td.innerHTML = movie[movieProperty];
                });

                // The td for the "available" property is a check
                let td = domConstruct.create("td", null, tr);
                if (movie.available) {
                    domConstruct.create("i", {"class": "fas fa-check"}, td);
                }

                // Create the update and delete buttons
                this._createUpdateDeleteTD(tr, true, movie.id);
                this._createUpdateDeleteTD(tr, false, movie.id);
            });
        },

        /**
         * Create the Update or Delete table column (td)
         * @param tr - The table row dom
         * @param updateButton - If we're creating the update or delete button
         * @private
         */
        _createUpdateDeleteTD: function(tr, updateButton, movieId) {
            let buttonClass,
                onClickMethod;

            if (updateButton) {
                buttonClass = "fas fa-pencil-alt";
                onClickMethod = "_onClickUpdateMovie";
            } else {
                buttonClass = "fas fa-times";
                onClickMethod = "_onClickDeleteMovie";
            }

            let td = domConstruct.create("td", null, tr),
                a = domConstruct.create("button", 
                    {
                        "type": "button", 
                        "class": "btn btn-primary " + buttonClass,
                        "movieid": movieId, 
                        "onclick": lang.hitch(this, onClickMethod)
                    }, td);
        },

        /**
         * On Click Add movie button
         * @param event - An event
         * @private
         */
        _onClickAddMovie: function(event) {
            event.preventDefault();

            this._addMovie.openSaveMovieDialog();
        },

        /**
         * On Click Update movie button
         * @param event - An event
         * @private
         */
        _onClickUpdateMovie: function(event) {
            let movieId = event.target.getAttribute("movieid");
            event.preventDefault();

            let selectedMovie = this._movieData.filter(movie => movie.id == movieId);

            this._addMovie.openUpdateMovieDialog(selectedMovie[0]);
        },

        /**
         * On Click Delete movie button
         * @param event - An event object
         * @private
         */
        _onClickDeleteMovie: function(event) {
            let movieId = event.target.getAttribute("movieid");
            event.preventDefault();

            let selectedMovie = this._movieData.filter(movie => movie.id == movieId);

            this._movieToDelete = selectedMovie[0];

            this._deleteMovieDialog.show();
        },

        /**
         * Close the delete movie dialog
         * @param event - An event object
         */
        onCloseDeleteDialog: function(event) {
            event.preventDefault();
            this._deleteMovieDialog.hide();
        },

        /**
         * On delete movie
         * @param event - An event object
         */
        onDeleteMovie: function(event) {
            event.preventDefault();
            this._serviceCall(false);
        },

    });
});
//# sourceURL=Movies.js
