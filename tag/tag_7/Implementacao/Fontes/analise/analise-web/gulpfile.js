var gulp = require('gulp');
var inject = require('gulp-inject');
var mainBowerFiles = require('gulp-main-bower-files');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var gulpFilter = require('gulp-filter');
var order = require('gulp-order');

gulp.task('generate-vendor', function() {
    var filterJS = gulpFilter('**/*.js', { restore: true });
    return gulp.src('./bower.json')
        .pipe(mainBowerFiles({
            overrides: {
                'bootstrap': {
                    main: [
                        './dist/js/bootstrap.js',
                        './dist/css/bootstrap.min.css',
                        './dist/fonts/*.*'
                    ]
                },
                'jquery-ui': {
                	main: [
                	    './jquery-ui.min.js',
                	    './themes/base/jquery-ui.min.css',
                	    './themes/base/images/*.*'
        	        ]
                }
            }
        }))
        .pipe(order(['components/jquery/dist/jquery.js', '*'], { base: './' }))
        .pipe(filterJS)
        //.pipe(concat('vendor.js'))
        .pipe(uglify())
        .pipe(filterJS.restore)
        .pipe(gulp.dest('./src/main/webapp/libs'));
});

gulp.task('inject-app', ['generate-vendor'], function() {
    
    var target = gulp.src('./src/main/webapp/index.jsp');
    var sources = gulp.src(['./src/main/webapp/libs/jquery/dist/jquery.js',
                            './src/main/webapp/libs/bootstrap/dist/js/bootstrap.js',
                            './src/main/webapp/libs/jquery-ui/jquery-ui.min.js',
                            './src/main/webapp/libs/bootstrap/dist/css/bootstrap.min.css',
                            './src/main/webapp/libs/jquery-ui/themes/base/jquery-ui.min.css',
                            './src/main/webapp/libs/**/*.js', 
                            './src/main/webapp/libs/**/*.css',
                            './src/main/webapp/js/libs/*.js',
                            './src/main/webapp/js/visent/*.js',
                            './src/main/webapp/css/visent/*.css',
                            './src/main/webapp/js/*.js',
                            './src/main/webapp/css/*.css'], 
                           {read: false, nodir: true});
    return target.pipe(inject(sources, {
            addRootSlash: false,
            ignorePath: 'src/main/webapp/'
        }))
        .pipe(gulp.dest('./src/main/webapp/'));
});

gulp.task('inject-login', ['inject-app'], function() {
    
    var target = gulp.src('./src/main/webapp/login.jsp');
    var sources = gulp.src(['./src/main/webapp/libs/jquery/dist/jquery.js',
                            './src/main/webapp/libs/bootstrap/dist/js/bootstrap.js',
                            './src/main/webapp/libs/jquery-ui/jquery-ui.min.js',
                            './src/main/webapp/libs/bootstrap/dist/css/bootstrap.min.css',
                            './src/main/webapp/libs/jquery-ui/themes/base/jquery-ui.min.css',
                            './src/main/webapp/css/*.css',
                            './src/main/webapp/js/util.js'], 
                           {read: false, nodir: true});
    return target.pipe(inject(sources, {
            addRootSlash: false,
            ignorePath: 'src/main/webapp/'
        }))
        .pipe(gulp.dest('./src/main/webapp/'));
});

gulp.task('default', ['inject-login']);