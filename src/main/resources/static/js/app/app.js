/**
 * 
 */

var app = angular.module('voiting', [ 'ngRoute' ]);

app.config(function($routeProvider) {
	$routeProvider.when('/admin', {
		templateUrl : 'views/admin.html',
		controller : 'AdminCtrl',
		controllerAs : 'actrl'
	}).when('/admin_theme/:id/:name', {
		templateUrl : 'views/adminThemes.html',
		controller : 'ThemeCtrl',
		controllerAs : 'tctrl'
	}).when('/client', {
		templateUrl : 'views/client.html',
		controller : 'ClientCtrl',
		controllerAs : 'cctrl'
	}).when('/client_vote/:id/:name/:status', {
		templateUrl : 'views/clientVotes.html',
		controller : 'VotesCtrl',
		controllerAs : 'vctrl'
	}).otherwise({
		redirectTo : '/client'
	});
});

app.controller("AdminCtrl", function($scope, $http) {
	var self = this;
	self.themes = [];
	self.theme = ({
		themeId : "",
		name : "",
		status : "",
	});
	self.votingForm = ({
		name : "",
		items : [],
	});
	self.reset = reset;
	self.successMessage = '';
    self.errorMessage = '';

	loadListThemes();

	function loadListThemes() {
		$http.get('/show_themes').then(function(result) {
			$scope.themes = result.data;
		}, function(result) {
			console.error("Error: " + result.status + " : " + result.data);
		});

	}

	$scope.submit = function() {
		$http.post('/register_theme', $scope.votingForm).then(function() {
			loadListThemes();
			console.log("theme is saved successfully! ");
			self.successMessage = 'theme is saved successfully! ';
            self.errorMessage='';
		}, function() {
			console.error("Error saving information ");
			self.successMessage = ' ';
            self.errorMessage='Error saving information!';
		});
	};

	$scope.startVoting = function(id) {
		$http.put('/start_theme/' + id).then(function(result) {
			loadListThemes();
		}, function(result) {
			var data = result.data;
			var status = result.status;
			alert("Error: " + status + ":" + data);
		});
	}

	$scope.stopVoting = function(id) {
		$http.put('/stop_theme/' + id).then(function(result) {
			loadListThemes();
		}, function(result) {
			var data = result.data;
			var status = result.status;
			alert("Error: " + status + ":" + data);
		});
	}

	$scope.showStatistic = function(id, name) {
		document.location.replace("#/admin_theme/" + id + "/" + name).then(
				function() {
					console.log("Person is chosen");
				}, function(result) {
					var data = result.data;
					var status = result.status;
					alert("Error: " + status + ":" + data);
				});
	}
	
	$scope.removeTheme = function(id) {
		$http.delete('/delete_theme/' +id)
		.then(function(result) {
			loadListThemes();
			console.log("theme is deleted successfully! ");
			self.successMessage = 'theme is deleted successfully! ';
            self.errorMessage='';
		}, function(result) {
			console.error("Error deleting information ");
			self.successMessage = ' ';
            self.errorMessage='Error deleting information!';
		});  
	}
	
	function reset() {
		self.successMessage = '';
		self.errorMessage = '';
		self.votingForm = {};
		$scope.myForm.$setPristine(); // reset Form
	}

});

app.controller("ThemeCtrl", function($scope, $http, $routeParams) {
	var self = this;
	self.votes = [];
	self.vote = {
		theme : {
			themeId : $routeParams.id,
			name : $routeParams.name
		},
		voteId : "",
		noItem : "",
		item : "",
		numberOfVotes : "",
		statOfVotes : ""
	};

	getThemeItems($routeParams.id);

	function getThemeItems(id) {
		$http.get('/show_theme/' + id).then(function(result) {
			$scope.votes = result.data;
		}, function(result) {
			console.error("Error: " + result.status + " : " + result.data);
		});
	}
});

app.controller("ClientCtrl", function($scope, $http) {
	var self = this;
	self.themes = [];
	self.theme = ({
		themeId : "",
		name : "",
		status : "",
	});

	loadListThemes();

	function loadListThemes() {
		$http.get('/show_themes').then(function(result) {
			$scope.themes = result.data;
		}, function(result) {
			console.error("Error: " + result.status + " : " + result.data);
		});

	}

	$scope.showStatistic = function(id, name, status) {
		document.location.replace("#/client_vote/" + id + "/" + name + "/" + status).then(
				function() {
					console.log("Person is chosen");
				}, function(result) {
					var data = result.data;
					var status = result.status;
					alert("Error: " + status + ":" + data);
				});
	}

});

app.controller("VotesCtrl", function($scope, $http, $routeParams) {
	var self = this;
	self.votes = [];
	self.vote = {
		theme : {
			themeId : $routeParams.id,
			name : $routeParams.name, 
			status : $routeParams.status
		},
		voteId : "",
		noItem : "",
		item : "",
		numberOfVotes : "",
		statOfVotes : ""
	};
	self.successMessage = '';
    self.errorMessage = '';

	getThemeItems($routeParams.id);

	$scope.toVote = function(themeId, voteId) {
		$http.put('/vote/' + themeId + '/' + voteId).then(function() {
			getThemeItems(themeId);
			self.successMessage = 'the vote is saved successfully! ';
            self.errorMessage='';
            console.log("person is saved successfully! ")			          
		}, function() {
			self.successMessage = '';
            self.errorMessage='error with saving the vote!';
            console.error("Error with saving information");
		});
	};

	function getThemeItems(id) {
		$http.get('/show_theme/' + id).then(function(result) {
			$scope.votes = result.data;
		}, function(result) {
			console.error("Error: " + result.status + " : " + result.data);
		});
	}
});
