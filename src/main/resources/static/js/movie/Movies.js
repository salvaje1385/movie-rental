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
    "dijit/form/TextBox",
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
) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {

        // set our template
        templateString: template,

        _moviesServiceUrl: "movies",

        _methods: new Methods(),

        _addMovie: new AddMovie(),

        _movieData: null,

        _movieToDelete: null,

        _ascendingSort: false,

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
                        this._movieData = response.content;
                        this._createMovieTable(response.content);
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
         * @param content - The content to populate the table
         * @private
         */
        _createMovieTable: function(content) {
            let movieProperties = ["title", "description", "stock", "rentalPrice", "salePrice"];

            // Empty the table before creating it
            domConstruct.empty(this.moviesTable);

            // Create the movie table
            content.map(movie => {
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

        /**
         * On search
         * @private
         */
        _onSearch: function() {
            let searchString = this._search.get("value"),
                searchResult = [];

            // Filter the movie list by the search string (comparing with the 
            // title and description properties)
            searchResult = this._movieData.filter(
                movie => movie.title.toLowerCase().indexOf(searchString.toLowerCase()) > -1
                        || movie.description.toLowerCase().indexOf(searchString.toLowerCase()) > -1
            );

            this._createMovieTable(searchResult);
        },

        /**
         * On search
         * @private
         */
        _sortByTitle: function() {
            this._movieData.sort(this._ascendingSort 
                ? this._compareMoviesAsc : this._compareMoviesDesc);

            this._createMovieTable(this._movieData);
            
            this._ascendingSort = !this._ascendingSort;
        },

        /**
         * Compare movies by title, to order them ascending
         * @param {Object} a - The element a
         * @param {Object} b - The element b
         * @return {Boolean} - The comparison result
         * @private
         */
        _compareMoviesAsc: function(a, b) {
            // Use toUpperCase() to ignore character casing
            const movieA = a.title.toUpperCase();
            const movieB = b.title.toUpperCase();
          
            let comparison = 0;
            if (movieA > movieB) {
              comparison = 1;
            } else if (movieA < movieB) {
              comparison = -1;
            }
            return comparison;
        },

        /**
         * Compare movies by title, to order them descending
         * @param {Object} a - The element a
         * @param {Object} b - The element b
         * @return {Boolean} - The comparison result
         * @private
         */
        _compareMoviesDesc: function(a, b) {
            // Use toUpperCase() to ignore character casing
            const movieA = a.title.toUpperCase();
            const movieB = b.title.toUpperCase();
          
            let comparison = 0;
            if (movieA < movieB) {
              comparison = 1;
            } else if (movieA > movieB) {
              comparison = -1;
            }
            return comparison;
        },

    });
});
//# sourceURL=Movies.js
