define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dojo/dom",
    "dojo/dom-construct",
    "dojo/_base/lang",
    "dojo/dom-class",
    "/js/movierental/Methods.js",
    "dojo/json",
    "dojo/text!./templates/AddMovie.html",
    "dijit/Dialog",
    "dijit/form/Form",
    "dijit/form/ValidationTextBox",
    "dijit/form/Textarea",
    "dijit/form/CheckBox",
    "dijit/form/Button",
],function(
    declare, 
    _WidgetBase, 
    _TemplatedMixin, 
    _WidgetsInTemplateMixin,
    dom, 
    domConstruct,
    lang,
    domClass,
    Methods,
    JSON,
    template
) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {

        // set our template
        templateString: template,

        _moviesServiceUrl: "movies",

        _theresNumberError: false,

        _methods: new Methods(),

        _movieToUpdate: null,

        postCreate: function() {
        },

        /**
         * Open the save movie dialog
         */
        openSaveMovieDialog: function() {
            this._movieToUpdate = null;
            this._initSaveDialog();
            this.addMovieDialog.show();
        },

        /**
         * Open the update movie dialog
         */
        openUpdateMovieDialog: function(movie) {
            console.log("openUpdateMovieDialog:", movie);

            this._movieToUpdate = movie;

            this._title.set("value", movie.title);
            this._description.set("value", movie.description);
            this._stock.set("value", movie.stock);
            this._rentalPrice.set("value", movie.rentalPrice);
            this._salePrice.set("value", movie.salePrice);
            this._available.set("value", movie.available);
            this.addMovieDialog.show();
        },

        /**
         * Close the save movie dialog
         */
        onCloseDialog: function() {
            this.addMovieDialog.hide();
        },

        /*
         * Init the Save Dialog
         */
        _initSaveDialog: function() {
            this._addMovieForm.reset();
        },

        /**
         * On submit the form
         * @param event - An event
         * @private
         */
        _onCreateMovie: function(event) {
            event.preventDefault();

            // Clear the error message
            domClass.add(this._errorMessage, "hidden");
            this._theresNumberError = false;

            // If the form is valid, proceed
            if (this._addMovieForm.isValid()) {
                let formValues = this._addMovieForm.get("value");
                // Convert the available value to boolean
                formValues.available = formValues.availableValue.length > 0;

                formValues.stock = this._validateNumberFieldError(
                    "Please insert a number in the Stock field", formValues.stockValue);
                formValues.rentalPrice = this._validateNumberFieldError(
                    "Please insert a number in the Rental Price field", formValues.rentalPriceValue);
                formValues.salePrice = this._validateNumberFieldError(
                    "Please insert a number in the Sale Price field", formValues.salePriceValue);

                if (!this._theresNumberError) {
                    this._saveMovie(formValues);
                }
            }
        },

        /**
         * Validate if the given value is a number, if not, show an error message
         * @param errorMsg - The error message to show 
         * @param strVal - The value as string
         * @return The string value converted to number
         * @private
         */
        _validateNumberFieldError: function(errorMsg, strVal) {
            let numberVal = strVal * 1; // Convert the string value to number
                numberValReturn = strVal.length > 0 ? strVal * 1 : null; // Return value

            if (!this._theresNumberError && strVal.length > 0) {
                let isANumber = !isNaN(numberVal); // Check if value is a number

                this._errorMessage.innerHTML = errorMsg;

                domClass.toggle(this._errorMessage, "hidden", isANumber);

                if (!isANumber) {
                    this._theresNumberError = true;
                }
            }
            return numberValReturn;
        },

        /**
         * Call the service to save the movie object
         * @param data - The data to post
         * @private
         */
        _saveMovie: function(data) {
            let updatingMovie = this._movieToUpdate != null,
                serviceUrl = this._moviesServiceUrl,
                httpMethod = "POST";

            if (updatingMovie) {
                serviceUrl = serviceUrl + "/" + this._movieToUpdate.id;
                httpMethod = "PUT";
            }

            let dataObj = {
                title: data.title,
                description: data.description,
                stock: data.stock,
                rentalPrice: data.rentalPrice,
                salePrice: data.salePrice,
                available: data.available
            },
            params = {
                    url: serviceUrl,
                    "data": JSON.stringify(dataObj),
                },
                responseOnSuccess = lang.hitch(this, function(response) {
                    // Create the movie table with the data from the service
                    this.onCloseDialog();
                    location.reload(true);
                }),
                responseOnError = lang.hitch(this, function(error) {
                    console.log("ERROR! AddMovie: _saveMovie:", error);
                });

            // Call the service
            this._methods.callService(
                params,
                httpMethod,
                responseOnSuccess,
                responseOnError
            );
        },

    });
});
//# sourceURL=AddMovies.js
