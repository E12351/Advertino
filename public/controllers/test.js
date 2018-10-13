var app = angular
    .module('myApp', [])
    .config(function($interpolateProvider) {
        $interpolateProvider.startSymbol('{[{');
        $interpolateProvider.endSymbol('}]}');
      })
    .controller('testController', function($scope) {

        var Details = {
            firstname: 'Amila',
            lastName: 'Indrajith'
          };

        var image = {
            name: 'home',
            path: 'images/home.png'
          };

        var tech =
           {
              name: 'c', likes: 0, dislikes: 0
            };

        $scope.tech = tech;
        $scope.Details = Details;
        $scope.image = image;
        $scope.name = 'Amila Indrajith Ukwattage';

        $scope.incrementLike = function(tech) {
            console.log('incrementLike');
            tech.likes++;
          };
      });
