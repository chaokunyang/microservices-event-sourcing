require.config({
    // 所有脚本的根目录，相对于 html
    baseUrl: 'assets/',
    // alias libraries paths.  Must set 'angular'
    paths:{
        // angular 的路径， 相对于 baseUrl
        "angular":"lib/angular/angular.min",
        "angularAMD":"lib/angular/angularAMD",
        "angular-route":"lib/angular/angular-route.min",
        'angular-ui-router': 'lib/angular/angular-ui-router.min',
        'angular-resource': 'lib/angular/angular-resource.min',
        'jquery': 'lib/jquery/jquery.min',
        'jquery.uploadify': 'lib/jquery/jquery.uploadify.min',
        'bootstrap': 'lib/bootstrap/js/bootstrap.min',
        'angular-toaster': 'lib/angular/angular-toaster.min',
        'ngAnimate': 'lib/angular/angular-animate.min',
        'common': 'js/common'
    },
    // Add angular modules that does not support AMD out of the box, put it in a shim
    shim:{
        // angular does not support AMD out of the box, put it in a shim. once loaded, use the global 'angular' as the module value. 使用了angularAMD后也可以不配置它。
        "angular":{
            exports:"angular",
            deps: ['jquery']
        },
        'angularAMD': ['angular'], // 依赖angular
        "angular-route":{
            deps: ['angular'] // 依赖angular
        },
        'angular-ui-router': [ 'angular' ], // 也可以这样写依赖关系
        'angular-resource': [ 'angular' ],
        'bootstrap': ['jquery'],
        'angular-toaster': ['angular', 'ngAnimate']
    },

    deps: ['js/app'] // 启动应用，也可以使用下面的require语句
});
// require(['app'], function(app){
//
// });
