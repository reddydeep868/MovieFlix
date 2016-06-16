(function () {
    'use strict';
    angular.module('movieflix')
        .service('titleService', titleService);

    titleService.$inject = ['$http', '$q', 'CONFIG'];

    function titleService($http, $q, CONFIG) {
        var self = this;
        self.createTitle = createTitle;
        self.fetchAllTitles = fetchAllTitles;
        self.fetchTitleById = fetchTitleById;
        self.updateTitle = updateTitle;
        self.deleteTitle = deleteTitle;
        self.postComment = postComment;
        self.postRate = postRate;
        self.getAvgRating = getAvgRating;

        function createTitle(title) {
            return $http.post(CONFIG.API_HOST + '/titles/admin/create/', title)
                .then(successFn, errorFn);
        }

        function fetchAllTitles() {
            console.log('inside title service');
            return $http.get(CONFIG.API_HOST + '/titles/')
                .then(successFn, errorFn);
        }

        function postComment(userId, titleId, comment) {
            return $http.post(CONFIG.API_HOST + '/rating/' + userId + '/' + titleId + '?comment=' + comment)
                .then(successFn, errorFn);
        }

        function fetchTitleById(id) {
            return $http.get(CONFIG.API_HOST + '/titles/' + id)
                .then(successFn, errorFn);
        }

        function updateTitle(title, id) {
            return $http.put(CONFIG.API_HOST + '/titles/admin/update/' + id, title)
                .then(successFn, errorFn);
        }

        function deleteTitle(id) {
            return $http.delete(CONFIG.API_HOST + '/titles/admin/delete/' + id)
                .then(successFn, errorFn);
        }

        function postRate(userId, titleId, rating) {
            return $http.post(CONFIG.API_HOST + '/rating/' + userId + '/' + titleId + '?rate=' + rating)
                .then(successFn, errorFn);
        }

        function getAvgRating(titleId) {
            return $http.get(CONFIG.API_HOST + '/getavgrating/' + titleId)
                .then(successFn, errorFn);
        }

        function successFn(response) {
            return response.data;
        }

        function errorFn(errResponse) {
            console.log('error getting data');
            return $q.reject(errResponse.statusText);
        }
    }
})();
