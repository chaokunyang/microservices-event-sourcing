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
        'ui.bootstrap': 'lib/angular/ui-bootstrap-tpls-0.12.0.min',
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
        'ngAnimate': ['angular'],
        'angular-toaster': ['angular', 'ngAnimate'],
        'ui.bootstrap': ['angular']
    },

    // deps: ['js/app', 'common/sharedServices', 'js/user/user'] // 不要这样启动应用，应该使用下面的require语句在所有模块加载完成后启动angular应用
});
require(['angularAMD', 'js/app', 'common/sharedServices', 'js/user/user'], function(angularAMD, app){
    angularAMD.bootstrap(app); // 使用deps启动应用时需要在app里面直接angularAMD.bootstrap(app)，这会在app模块加载时直接开始渲染页面，而某些controller、filter、config等此时可能还没有从服务器获取下来或者还没有加载完成(即app.controller、app.config等还没有执行完毕)，这时加载就会报错或者功能不全，应该在main.js里面的require语句里面加载完所有的依赖后，在回调函数里面启动app：angularAMD.bootstrap(app)
});
