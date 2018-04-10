/*=================================================================================================
Project:  Blockus
Version:  1.1.0
Last change:  07/10/2016
Design by:  TemplatesPRO.com.br
---------------------------------------------------------------------------------------------------

INDEX:
DOCUMENT.READY
  01) PRELOADER
  02) START - Set Logo, Slideshow and Section on Initialization
  03) BROWSER - Browser Detection
  04) BOOTSTRAP - Internet Explorer 10 in Windows 8 and Windows Phone 8 Fix
  05) BOOTSTRAP - Android Stock Browser Fix
  06) RESPONSIVE - Fix Navigation in Phone Landscape
  07) NAVIGATION - Browser History (PushState)
  08) HOME - Slideshow Carousel
  09) HOME - Stop Videos
  10) HOME - Form Subscription
  11) HOME - Form Subscription Modal
  12) FEATURES - Stats Counter
  13) FEATURES - Slide (Swipe and Desktop Draggable)
  14) OVERVIEW - ".tab-pane" Height
  15) OVERVIEW - Stop Video on Collapse
  16) OVERVIEW - Donuts Chart
  17) OVERVIEW - Newsletter (Form Mailchimp Subscription Ajax)
  18) GALLERY - Isotope Filter Gallery
  19) GALLERY - ".slide-gallery" Height
  20) GALLERY - Slide (Swipe and Desktop Draggable)
  21) GALLERY (Modal) - Slide (Swipe and Desktop Draggable)
  22) GALLERY (Modal) - ".project-info-modal" Height
  23) GALLERY (Modal/Lightbox) - Images Responsives (Retina)
  24) TESTIMONIALS - Slide (Swipe and Desktop Draggable)
  25) PRICES - Slide (Swipe and Desktop Draggable)
  26) TEAM - Slide (Swipe and Desktop Draggable)
  27) BLOG - Blog Tabs Carousel
  28) BLOG - Blog Carousel
  29) CONTACT - Form Contact Ajax
  30) CONTACT - Slide (Swipe and Desktop Draggable)

WINDOW.SCROLL
  31) RESPONSIVE - Fix Sliders Controls in Phone Landscape
  32) RESPONSIVE - Fix Section Features Height in Phone Landscape
=================================================================================================*/

/* DOCUMENT.READY ********************************************************************************/
$(document).ready(function () {
  'use strict';
  
  var windowWidth = $(window).width(),
    windowHeight = $(window).height();

  /*----------------------------------------------------------------------------------------------
   01) PRELOADER
   ----------------------------------------------------------------------------------------------*/
  $('.spinner').fadeOut(); // will first fade out the loading animation
  $('.preloader').delay(350).fadeOut('slow'); // will fade out the white DIV that covers the website.
  

  /*----------------------------------------------------------------------------------------------
   02) START - Set Logo, Slideshow and Section on Initialization
   ----------------------------------------------------------------------------------------------*/
  // Logo start no collapse
  $('.navbar-brand img').attr('data-toggle', '');

  // Slideshow start animation
  $('.slideshow .slideshow-object').delay(350).css('display', 'inline-block');

  if (windowHeight < 479) {
    // Fix section start in phone landscape
    $('section').css('top', 'auto');
  }
  
  /*----------------------------------------------------------------------------------------------
   03) BROWSER - Browser Detection
   ----------------------------------------------------------------------------------------------*/
  var browserNameOrig = bowser.name,
    browserName = browserNameOrig.replace(/\s+/g, '').toLowerCase();

  $('html').addClass(browserName);
  
  /*----------------------------------------------------------------------------------------------
   04) BOOTSTRAP - Internet Explorer 10 in Windows 8 and Windows Phone 8 Fix
   ----------------------------------------------------------------------------------------------*/
  if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
    var msViewportStyle = document.createElement('style');

    msViewportStyle.appendChild(
      document.createTextNode(
        '@-ms-viewport{width:auto!important}'
      )
    );
    document.querySelector('head').appendChild(msViewportStyle);
  }
  

  /*----------------------------------------------------------------------------------------------
   05) BOOTSTRAP - Android Stock Browser Fix
   ----------------------------------------------------------------------------------------------*/
  var nua = navigator.userAgent,
    isAndroid = (nua.indexOf('Mozilla/5.0') > -1 && nua.indexOf('Android ') > -1 && nua.indexOf('AppleWebKit') > -1 && nua.indexOf('Chrome') === -1);

  if (isAndroid) {
    $('select.form-control').removeClass('form-control').css('width', '100%');
  }
  

  /*----------------------------------------------------------------------------------------------
   06) RESPONSIVE - Fix Navigation in Phone Landscape
   ----------------------------------------------------------------------------------------------*/
  if (windowHeight < 479) {
    $('.navbar-collapse').on('show.bs.collapse', function () {
      $('.navbar').css('position', 'fixed');
      $('.main').css('padding-top', '60px');
      $('section').css('position', 'fixed');
      $('section').css('top', '60px');
    });

    $('.navbar-collapse').on('hide.bs.collapse', function () {
      $('.navbar').css('position', 'relative');
      $('html, body').animate({ scrollTop: 0 }, 0);
      $('.main').css('padding-top', '0');
      setTimeout(function () {
        $('section').css('position', 'relative');
        $('section').css('top', 'auto');
      }, 400);
    });
  }
  
  
  /*----------------------------------------------------------------------------------------------
   07) NAVIGATION - Browser History (PushState)
   ----------------------------------------------------------------------------------------------*/
  // Click links menu
  $('.navbar-fixed-top a[href^="#"]').on('click', function () {
    history.pushState({}, '', $(this).attr('href'));
    $('.navbar-fixed-top').addClass('top-nav-collapse');
    $('a[href^="#home"]').on('hidden.bs.tab', function () {
      $('.navbar-brand').attr('href', '#home');
    });
  });

  $('.navbar-fixed-top a[href^="#home"]').on('click', function () {
    $('.navbar-fixed-top').removeClass('top-nav-collapse');
    $('.navbar-brand').attr('href', '');
    $('a[href^="#home"]').on('shown.bs.tab', function () {
      history.replaceState({}, document.title, $(location).attr('pathname'));
      $('.navbar-brand').attr('href', '');
    });
  });

  // Click logo
  $('.navbar-brand').on('click', function () {
    history.pushState({}, '', $(this).attr('href'));
    $('.navbar-fixed-top .navbar-nav>li').removeClass('active');
    $('.navbar-fixed-top .navbar-nav>li:first-child').addClass('active');
    $('.navbar-fixed-top').removeClass('top-nav-collapse');
    $('a[href^="#home"]').on('shown.bs.tab', function () {
      history.replaceState({}, document.title, $(location).attr('pathname'));
      $('.navbar-brand').attr('href', '');
      $('.navbar-brand img').attr('data-toggle', '');
    });
  });

  $('.navbar-collapse').on('show.bs.collapse', function () {
    $('.navbar-brand img').attr('data-toggle', 'collapse');
  });

  $('.navbar-collapse').on('hide.bs.collapse', function () {
    $('.navbar-brand img').attr('data-toggle', '');
  });

  // Click any link (a.btn) on site
  $('section a.btn[href^="#"]:not(.btn-collapse)').on('click', function () {
    var liAnch = $(this).attr('href');
    liAnch = liAnch.replace('#', '');
    history.pushState({}, '', $(this).attr('href'));
    $('.navbar-fixed-top').addClass('top-nav-collapse');
    $('.navbar-fixed-top .navbar-nav>li').removeClass('active');
    $('.navbar-fixed-top .navbar-nav>li.anch-' + liAnch + '').addClass('active');
    $('.navbar-brand').attr('href', '#home');
  });
  
  // Go Back History
  window.onhashchange = function() {
    // store the currently selected tab in the hash value
    $(".navbar-nav a").on("shown.bs.tab", function(e) {
      var id = $(e.target).attr("href").substr(1);
      window.location.hash = id;
    });

    // on load of the page: switch to the currently selected tab
    if (location.hash) {
      var hash = window.location.hash;
      $('.navbar-nav a[href="' + hash + '"]').tab('show');
      if (location.hash === '#home') {
        history.replaceState({}, document.title, $(location).attr('pathname'));
        $('.navbar').removeClass('top-nav-collapse');
      } else {
        $('.navbar').addClass('top-nav-collapse');
      }
    } else {
      $('.navbar-nav a[href^="#home"]').tab('show');
      $('.navbar').removeClass('top-nav-collapse');
    }
  }
  
  // Refresh (F5)
  setTimeout(function () {
    // store the currently selected tab in the hash value
    $(".navbar-nav a").on("shown.bs.tab", function(e) {
      var id = $(e.target).attr("href").substr(1);
      window.location.hash = id;
    });

    // on load of the page: switch to the currently selected tab
    var hash = window.location.hash;
    $('.navbar-nav a[href="' + hash + '"]').tab('show');
    
    if (location.hash) {
      $('.navbar').addClass('top-nav-collapse');
      if (location.hash === '#home') {
        history.replaceState({}, document.title, $(location).attr('pathname'));
        $('.navbar').removeClass('top-nav-collapse');
      } else {
        $('.navbar-brand').attr('href', '#home');
      }
    }
  }, 0);
  

  /*----------------------------------------------------------------------------------------------
   08) HOME - Slideshow Carousel
   ----------------------------------------------------------------------------------------------*/
  $('#slideshow-carousel, #blog-carousel').carousel({
    interval: 10000999
  });
  

  /*----------------------------------------------------------------------------------------------
   09) HOME - Stop Videos
   ----------------------------------------------------------------------------------------------*/
  // On click on Slideshow
  $('#slideshow-carousel').on('slide.bs.carousel', function () {
    $('iframe.slideshow-object').attr('src', $('iframe.slideshow-object').attr('src'));
    $('iframe.slideshow-object').removeData();
  });

  // On click any link on site to go to another page
  $('.navbar-brand, section a[href^="#"]:not(.btn-collapse)').on('click', function () {
    $('iframe.slideshow-object').attr('src', $('iframe.slideshow-object').attr('src'));
    $('iframe.slideshow-object').removeData();
    $('iframe.overview-presentation').attr('src', $('iframe.overview-presentation').attr('src'));
    $('iframe.overview-presentation').removeData();
  });

  // On click on Navbar
  $('.navbar a[href^="#"]').on('hide.bs.tab', function () {
    $('iframe.slideshow-object').attr('src', $('iframe.slideshow-object').attr('src'));
    $('iframe.slideshow-object').removeData();
    $('iframe.overview-presentation').attr('src', $('iframe.overview-presentation').attr('src'));
    $('iframe.overview-presentation').removeData();
  });

  /*----------------------------------------------------------------------------------------------
   10) HOME - Form Subscription
   ----------------------------------------------------------------------------------------------*/
  $('.submit-form-trial').on('click', function () {
    var proceed = true;
    //simple validation at client's end
    //loop through each field and we simply change border color to red for invalid fields
    $('.trial-form input[required=true]').each(function () {
      $(this).css('border-color', '');
      if (!$.trim($(this).val())) { //if this field is empty
        $(this).css('border-color', '#c00'); //change border color to red
        proceed = false; //set do not proceed flag
      }
      //check invalid email
      var email_reg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
      if ($(this).attr('type') === 'email' && !email_reg.test($.trim($(this).val()))) {
        $(this).css('border-color', '#c00'); //change border color to red
        proceed = false; //set do not proceed flag
      }
    });

    if (proceed) { //everything looks good! proceed...
      //get input field values data to be sent to server
      var post_data = {
        'user_trial_name' : $('input[name=trial-name]').val(),
        'user_trial_email': $('input[name=trial-email]').val(),
        'user_trial_phone': $('input[name=trial-phone]').val()
      };

      //Ajax post data to server
      $.post('subscription.php', post_data, function (response) {
        if (response.type === 'error') { //load json data from server and output message
          var output = '<div class="alert alert-danger alert-dismissible" role="alert"><button class="close" data-dismiss="alert" type="button"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><strong>Error:&nbsp;&nbsp;</strong>' + response.text + '</div><div class="alert-overlay"></div>';
          $('.alerts').hide().html(output).slideDown();
        } else {
          var output = '<div class="alert alert-success alert-dismissible" role="alert"><button class="close" data-dismiss="alert" type="button"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><strong>Success!&nbsp;&nbsp;</strong>' + response.text + '</div><div class="alert-overlay"></div>';
          //reset values in all input fields
          $('.trial-form input').val('');
          $('.alerts').hide().html(output).slideDown();
          $('.alerts').delay(10000).slideUp();
        }
      }, 'json');
    }
  });

  //reset previously set border colors and hide all message on .keyup()
  $('.trial-form input[required=true]').keyup(function () {
    $(this).css('border-color', '');
    $('.alerts').slideUp();
  });
  

  /*----------------------------------------------------------------------------------------------
   11) HOME - Form Subscription Modal
   ----------------------------------------------------------------------------------------------*/
  $('.submit-form-modal-trial').on('click', function () {
    var proceed = true;
    //simple validation at client's end
    //loop through each field and we simply change border color to red for invalid fields
    $('.modal-trial-form input[required=true]').each(function () {
      $(this).css('border-color', '');
      if (!$.trim($(this).val())) { //if this field is empty
        $(this).css('border-color', '#c00'); //change border color to red
        proceed = false; //set do not proceed flag
      }
      //check invalid email
      var email_reg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
      if ($(this).attr('type') === 'email' && !email_reg.test($.trim($(this).val()))) {
        $(this).css('border-color', '#c00'); //change border color to red
        proceed = false; //set do not proceed flag
      }
    });

    if (proceed) { //everything looks good! proceed...
      //get input field values data to be sent to server
      var post_data = {
        'user_modal_trial_name' : $('input[name=modal-trial-name]').val(),
        'user_modal_trial_email': $('input[name=modal-trial-email]').val(),
        'user_modal_trial_phone': $('input[name=modal-trial-phone]').val()
      };

      //Ajax post data to server
      $.post('modal-subscription.php', post_data, function (response) {
        if (response.type === 'error') { //load json data from server and output message
          var output = '<div class="alert alert-danger alert-dismissible" role="alert"><button class="close" data-dismiss="alert" type="button"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><strong>Error:&nbsp;&nbsp;</strong>' + response.text + '</div><div class="alert-overlay"></div>';
          $('.alerts').hide().html(output).slideDown();
        } else {
          var output = '<div class="alert alert-success alert-dismissible" role="alert"><button class="close" data-dismiss="alert" type="button"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><strong>Success!&nbsp;&nbsp;</strong>' + response.text + '</div><div class="alert-overlay"></div>';
          //reset values in all input fields
          $('.modal-trial-form input').val('');
          $('.alerts').hide().html(output).slideDown();
          $('.alerts').delay(10000).slideUp();
        }
      }, 'json');
    }
  });

  //reset previously set border colors and hide all message on .keyup()
  $('.modal-trial-form input[required=true]').keyup(function () {
    $(this).css('border-color', '');
    $('.alerts').slideUp();
  });
  

  /*----------------------------------------------------------------------------------------------
   12) FEATURES - Stats Counter
   ----------------------------------------------------------------------------------------------*/
  if ($('section.features').length) {
    var optionsCounterStats = {
      useEasing : true,
      useGrouping : true,
      separator : '.',
      decimal : ',',
      prefix : '',
      suffix : ''
    },
      c1 = new CountUp('counter-1', 0, 125, 2, 2, optionsCounterStats),
      c2 = new CountUp('counter-2', 0, 250, 2, 2, optionsCounterStats),
      c3 = new CountUp('counter-3', 0, 375, 2, 2, optionsCounterStats),
      c4 = new CountUp('counter-4', 0, 500, 2, 2, optionsCounterStats);
    $('section.features .section-footer label:nth(0)').on('click', function () {
      c1.start();
    });
    $('section.features .section-footer label:nth(1)').on('click', function () {
      c2.start();
    });
    $('section.features .section-footer label:nth(2)').on('click', function () {
      c3.start();
    });
    $('a[href="#features"]').on('shown.bs.tab', function () {
      setTimeout(function () {
        c4.start();
      }, 600);
    });
  }
  

  /*----------------------------------------------------------------------------------------------
   13) FEATURES - Slide (Swipe and Desktop Draggable)
   ----------------------------------------------------------------------------------------------*/
  if ((windowWidth < 768) && ($('section.features').length)) {
    $('a[href="#features"]').on('shown.bs.tab', function () {
      $('.slide-features').not('.slick-initialized').slick({
        responsive: [
          {
            breakpoint: 768,
            settings: {
              slidesToShow: 1,
              slidesToScroll: 1,
              speed: 600,
              mobileFirst: true,
              useTransform: true,
              cssEase: 'ease-in-out'
            }
          }
        ]
      });
    });
  }
  

  /*----------------------------------------------------------------------------------------------
   14) OVERVIEW - ".tab-pane" Height
   ----------------------------------------------------------------------------------------------*/
  var numPanels = $('.overview .panel-group .panel').length;

  if ((windowWidth < 768) && (windowHeight > 479) && ($('section.overview').length)) {
    $('a[href="#overview"]').on('shown.bs.tab', function () {
      var navbarHeight = $('.navbar .navbar-header').outerHeight(true),
        sectionheaderHeight = $('.overview .section-header').outerHeight(true),
        panelheadingHeight = $('.overview #overview-heading-1').outerHeight(true) * numPanels,
        sectionfooterHeight = $('.overview .section-footer').outerHeight(true),
        footerHeight = $('.footer').outerHeight(true),
        totalHeight = navbarHeight + sectionheaderHeight + panelheadingHeight + sectionfooterHeight + footerHeight,
        tabpaneHeight = (windowHeight - totalHeight);

      $('.overview .tab-pane').css('height', tabpaneHeight);
    });
  }
  

  /*----------------------------------------------------------------------------------------------
   15) OVERVIEW - Stop Video on Collapse
   ----------------------------------------------------------------------------------------------*/
  $('#overview-accordion').on('hide.bs.collapse', function () {
    $(this).find('iframe').attr('src', $(this).find('iframe').attr('src'));
    $(this).removeData();
  });
  

  /*----------------------------------------------------------------------------------------------
   16) OVERVIEW - Donuts Chart
   ----------------------------------------------------------------------------------------------*/
  $('#overview-accordion').on('shown.bs.collapse', function () {
    $('#doughnutChart').drawDoughnutChart([
      {
        title: 'Google Plus',
        value : 10,
        color: '#fd0'
      },
      {
        title: 'Youtube',
        value:  20,
        color: '#fc0'
      },
      {
        title: 'Twitter',
        value:  30,
        color: '#fb0'
      },
      {
        title: 'Facebook',
        value:  40,
        color: '#fa0'
      }
    ]);
  });

  $('#overview-accordion').on('hide.bs.collapse', function () {
    $('#doughnutChart > *, .doughnutTip').remove();
  });
  

  /*----------------------------------------------------------------------------------------------
   17) OVERVIEW - Newsletter (Form Mailchimp Subscription Ajax)
   ----------------------------------------------------------------------------------------------*/
  if ($('section.overview').length) {
    $('.subscription-mailchimp').ajaxChimp({
      callback: mailchimpCallback,
      //Replace this url with your own mailchimp post URL
      url: 'http://templatespro.us10.list-manage.com/subscribe/post?u=e1b67e42c0c37f1e5eb1c76c6&amp;id=376920061c'
    });

    function mailchimpCallback(resp) {
      if (resp.result === 'success') {
        $('.alerts').hide().html('<div class="alert alert-success alert-dismissible" role="alert"><button class="close" data-dismiss="alert" type="button"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><strong>Success!&nbsp;&nbsp;</strong>' + resp.msg + '</div><div class="alert-overlay"></div>').slideDown();
        $('.alerts').delay(10000).slideUp();
      } else if (resp.result === 'error') {
        $('.alerts').hide().html('<div class="alert alert-danger alert-dismissible" role="alert"><button class="close" data-dismiss="alert" type="button"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><strong>Error:&nbsp;&nbsp;</strong>' + resp.msg + '</div><div class="alert-overlay"></div>').slideDown();
      }
    }
  }
  

  /*----------------------------------------------------------------------------------------------
   18) GALLERY - Isotope Filter Gallery
   ----------------------------------------------------------------------------------------------*/
  //change is-checked class on buttons
  $('.filter-button-group').each(function (i, buttonGroup) {
    var $buttonGroup = $(buttonGroup);
    $buttonGroup.on('click', 'button', function () {
      $buttonGroup.find('.active').removeClass('active');
      $(this).addClass('active');
    });
  });

  if ((windowWidth > 767) && ($('section.gallery').length)) {
    //bind filter button click
    $('.filter-button-group').on('click', 'button', function () {
      // init Isotope
      var $grid = $('.grid').isotope({
        itemSelector: '.element-item'
      }),
        filterValue = $(this).attr('data-filter');

      $grid.isotope({ filter: filterValue });
    });
  } else if ((windowWidth < 768) && ($('section.gallery').length)) {
    $('.filter-button-group').on('click', 'button', function () {
      var filterValue = $(this).attr('data-filter');

      if (filterValue !== '*') {
        $('.slide-gallery').slick('slickUnfilter').css('opacity', '0');
        $('.slide-gallery').slick('slickFilter', '' + filterValue + '').animate({
          opacity: '1'
        });
      } else {
        $('.slide-gallery').slick('slickUnfilter').css('opacity', '0').animate({
          opacity: '1'
        });
      }
    });
  }
  

  /*----------------------------------------------------------------------------------------------
   19) GALLERY - ".slide-gallery" Height
   ----------------------------------------------------------------------------------------------*/
  if ((windowWidth > 767) && ($('section.gallery').length)) {
    $('a[href="#gallery"]').on('shown.bs.tab', function () {
      var navbarHeight = $('.top-nav-collapse .navbar-header').outerHeight(true),
        sectionheaderHeight = $('.gallery .section-header').outerHeight(true),
        filterbuttongroupHeight = $('.gallery .filter-button-group').outerHeight(true),
        footerHeight = $('.footer').outerHeight(true),
        totalHeight = navbarHeight + sectionheaderHeight + filterbuttongroupHeight + footerHeight,
        windowHeight = $(window).height(),
        slidegalleryHeight = (windowHeight - totalHeight);

      $('.gallery .slide-gallery').css('max-height', slidegalleryHeight);

      if (slidegalleryHeight > 500) {
        $('.gallery .slide-gallery').css('overflow-y', 'auto');
        $('.gallery .slide-gallery').css('overflow-x', 'hidden');
      } else {
        $('.gallery .slide-gallery').css('overflow', 'hidden');
      }

      $('.filter-button-group').on('click', 'button', function () {
        var slideheightDynamically = $('.gallery .slide-gallery').outerHeight();

        if (slideheightDynamically > 500) {
          $('.gallery .slide-gallery').css('overflow-y', 'auto');
          $('.gallery .slide-gallery').css('overflow-x', 'hidden');
        } else {
          $('.gallery .slide-gallery').css('overflow', 'hidden');
        }
      });
    });
  }
  

  /*----------------------------------------------------------------------------------------------
   20) GALLERY - Slide (Swipe and Desktop Draggable)
   ----------------------------------------------------------------------------------------------*/
  if ((windowWidth < 768) && ($('section.gallery').length)) {
    $('a[href="#gallery"]').on('shown.bs.tab', function () {
      $('.slide-gallery').not('.slick-initialized').slick({
        responsive: [
          {
            breakpoint: 768,
            settings: {
              slidesToShow: 1,
              slidesToScroll: 1,
              speed: 600,
              mobileFirst: true,
              useTransform: true,
              cssEase: 'ease-in-out'
            }
          }
        ]
      });
    });
  }
  

  /*----------------------------------------------------------------------------------------------
   21) GALLERY (Modal) - Slide (Swipe and Desktop Draggable)
   ----------------------------------------------------------------------------------------------*/
  $('.modal').on('hidden.bs.modal', function (e) {
    $(this).find('iframe').attr('src', $(this).find('iframe').attr('src'));
    $(this).removeData();
  });

  $('.gallery-modal').on('shown.bs.modal', function (e) {
    $('.spinner').fadeOut();
    $('.preloader').delay(350).fadeOut('slow');

    $('.slide-gallery-modal').not('.slick-initialized').slick({
      slidesToShow: 1,
      slidesToScroll: 1,
      speed: 600,
      useTransform: true,
      cssEase: 'ease-in-out',
      responsive: [
        {
          breakpoint: 768,
          settings: {
            mobileFirst: true
          }
        }
      ]
    });
  });
  

  /*----------------------------------------------------------------------------------------------
   22) GALLERY (Modal) - ".project-info-modal" Height
   ----------------------------------------------------------------------------------------------*/
  if ((windowWidth < 768) && ($('section.gallery').length)) {
    $('.gallery-modal').on('shown.bs.modal', function (e) {
      var modalheaderHeight = $('.gallery-modal .modal-header').outerHeight(),
        modalsubheaderHeight = $('.gallery-modal .modal-subheader').outerHeight(),
        totalheaderHeight = modalheaderHeight + modalsubheaderHeight,
        windowHeight = $(window).height(),
        projectinfoHeight = (windowHeight - totalheaderHeight);

      $('.gallery-modal .project-info-modal, .gallery-modal .collapse').css('max-height', projectinfoHeight);
    });
  }
  

  /*----------------------------------------------------------------------------------------------
   23) GALLERY (Modal/Lightbox) - Images Responsives (Retina)
   ----------------------------------------------------------------------------------------------*/
  // Images Retina on Gallery Modal
  $('.gallery-modal').on('shown.bs.modal', function (e) {
    if (window.devicePixelRatio > 1) {
      var lowresImages = $('.gallery-modal img');

      lowresImages.each(function (i) {
        var lowres = $(this).attr('src'),
          highres = lowres.replace(".", "@2x.");

        $(this).attr('src', highres);
      });
    }
  });

  // Images Retina on Gallery Lightbox
  $('#blueimp-gallery').on('opened', function (event) {
    if (window.devicePixelRatio > 1) {
      var lowresImages = $('#blueimp-gallery img');

      lowresImages.each(function (i) {
        var lowres = $(this).attr('src'),
          typeImg = lowres.substring(lowres.lastIndexOf('.') + 1);

        if (typeImg === 'jpg') {
          var highres = lowres.replace(".jpg", "@2x.jpg");
        } else if (typeImg === 'png') {
          var highres = lowres.replace(".png", "@2x.png");
        }
        $(this).attr('src', highres);
      });
    }
  });
  

  /*----------------------------------------------------------------------------------------------
   24) TESTIMONIALS - Slide (Swipe and Desktop Draggable)
   ----------------------------------------------------------------------------------------------*/
  $('a[href="#testimonials"]').on('shown.bs.tab', function () {
    $('.slide-testimonials-bloquotes').not('.slick-initialized').slick({
      asNavFor: '.slide-testimonials-thumbs',
      slidesToShow: 1,
      slidesToScroll: 1,
      speed: 600,
      useTransform: true,
      cssEase: 'ease-in-out',
      arrows: false,
      fade: true,
      responsive: [
        {
          breakpoint: 768,
          settings: {
            mobileFirst: true
          }
        }
      ]
    });

    $('.slide-testimonials-thumbs').not('.slick-initialized').slick({
      asNavFor: '.slide-testimonials-bloquotes',
      slidesToShow: 3,
      slidesToScroll: 1,
      speed: 600,
      useTransform: true,
      cssEase: 'ease-in-out',
      centerMode: true,
      focusOnSelect: true,
      responsive: [
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 1,
            mobileFirst: true
          }
        }
      ]
    });
  });
  

  /*----------------------------------------------------------------------------------------------
   25) PRICES - Slide (Swipe and Desktop Draggable)
   ----------------------------------------------------------------------------------------------*/
  if ((windowWidth < 768) && ($('section.prices').length)) {
    $('a[href="#prices"]').on('shown.bs.tab', function () {
      $('.slide-prices').not('.slick-initialized').slick({
        responsive: [
          {
            breakpoint: 768,
            settings: {
              slidesToShow: 1,
              slidesToScroll: 1,
              speed: 600,
              mobileFirst: true,
              useTransform: true,
              cssEase: 'ease-in-out'
            }
          }
        ]
      });
    });
  }
  

  /*----------------------------------------------------------------------------------------------
   26) TEAM - Slide (Swipe and Desktop Draggable)
   ----------------------------------------------------------------------------------------------*/
  if ((windowWidth < 768) && ($('section.team').length)) {
    $('a[href="#team"]').on('shown.bs.tab', function () {
      $('.slide-team').not('.slick-initialized').slick({
        speed: 600,
        useTransform: true,
        cssEase: 'ease-in-out',
        responsive: [
          {
            breakpoint: 768,
            settings: {
              slidesToShow: 2,
              slidesToScroll: 2,
              mobileFirst: true
            }
          },
          {
            breakpoint: 480,
            settings: {
              slidesToShow: 1,
              slidesToScroll: 1,
              mobileFirst: true
            }
          }
        ]
      });
    });
  }
  

  /*----------------------------------------------------------------------------------------------
   27) BLOG - Blog Tabs Carousel
   ----------------------------------------------------------------------------------------------*/
  $('.blog .blog-tabs .title a').on('click', function () {
    if (!$(this).closest('li').hasClass('active')) {
      $('.blog .blog-tabs>li').removeClass('active');
    } else {
      $('.blog .blog-tabs>li').addClass('active');
    }
  });
  

  /*----------------------------------------------------------------------------------------------
   28) BLOG - Blog Carousel
   ----------------------------------------------------------------------------------------------*/
  $('#blog-carousel').carousel({
    interval: 10000999
  });
  

  /*----------------------------------------------------------------------------------------------
   29) CONTACT - Form Contact Ajax
   ----------------------------------------------------------------------------------------------*/
  $('.submit-form-contact').on('click', function () {
    var proceed = true;
    //simple validation at client's end
    //loop through each field and we simply change border color to red for invalid fields
    $('.contact-form input[required=true], .contact-form textarea[required=true]').each(function () {
      $(this).css('border-color', '');
      if (!$.trim($(this).val())) { //if this field is empty
        $(this).css('border-color', '#c00'); //change border color to red
        proceed = false; //set do not proceed flag
      }
      //check invalid email
      var email_reg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
      if ($(this).attr('type') === 'email' && !email_reg.test($.trim($(this).val()))) {
        $(this).css('border-color', '#c00'); //change border color to red
        proceed = false; //set do not proceed flag
      }
    });

    if (proceed) { //everything looks good! proceed...
      //get input field values data to be sent to server
      var post_data = {
        'user_fname'  : $('input#fname').val(),
        'user_lname'  : $('input#lname').val(),
        'user_email'  : $('input#email').val(),
        'user_phone'  : $('input#phone').val(),
        'subject'     : $('input#subject').val(),
        'msg'         : $('textarea#message').val()
      };

      //Ajax post data to server
      $.post('contact.php', post_data, function (response) {
        if (response.type === 'error') { //load json data from server and output message
          var output = '<div class="alert alert-danger alert-dismissible" role="alert"><button class="close" data-dismiss="alert" type="button"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><strong>Error:&nbsp;&nbsp;</strong>' + response.text + '</div><div class="alert-overlay"></div>';
          $('.alerts').hide().html(output).slideDown();
        } else {
          var output = '<div class="alert alert-success alert-dismissible" role="alert"><button class="close" data-dismiss="alert" type="button"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button><strong>Success!&nbsp;&nbsp;</strong>' + response.text + '</div><div class="alert-overlay"></div>';
          //reset values in all input fields
          $('.contact-form input, .contact-form textarea').val('');
          $('.alerts').hide().html(output).slideDown();
          $('.alerts').delay(10000).slideUp();
        }
      }, 'json');
    }
  });

  //reset previously set border colors and hide all message on .keyup()
  $('.contact-form input[required=true], .contact-form textarea[required=true]').keyup(function () {
    $(this).css('border-color', '');
    $('.alerts').slideUp();
  });
  

  /*----------------------------------------------------------------------------------------------
   30) CONTACT - Slide (Swipe and Desktop Draggable)
   ----------------------------------------------------------------------------------------------*/
  if ((windowWidth < 768) && ($('section.contact').length)) {
    $('a[href="#contact"]').on('shown.bs.tab', function () {
      $('.slide-contact').not('.slick-initialized').slick({
        responsive: [
          {
            breakpoint: 768,
            settings: {
              slidesToShow: 1,
              slidesToScroll: 1,
              speed: 600,
              mobileFirst: true,
              useTransform: true,
              cssEase: 'ease-in-out'
            }
          }
        ]
      });
    });
  }
  
});
/* END DOCUMENT.READY ****************************************************************************/



/* WINDOW.SCROLL *********************************************************************************/
$(window).scroll(function () {
  'use strict';
  
  var windowHeight = $(window).height();
  
  /*----------------------------------------------------------------------------------------------
   31) RESPONSIVE - Fix Sliders Controls in Phone Landscape
   ----------------------------------------------------------------------------------------------*/
  var scrollHeight = $(this).scrollTop();

  if (windowHeight < 479) {
    if ($(this).scrollTop() > 0) {
      $('.slick-arrow').css('top', 81 - scrollHeight);
    } else {
      $('.slick-arrow').css('top', 81 + scrollHeight);
    }
  }
  
  /*----------------------------------------------------------------------------------------------
   32) RESPONSIVE - Fix Section Features Height in Phone Landscape
   ----------------------------------------------------------------------------------------------*/
  if (windowHeight < 479) {
    if ($(this).scrollTop() > 50) {
      $('section.features').css('height', '100vh');
    } else {
      $('section.features').css('height', 'auto');
    }
  }

});
/* END WINDOW.SCROLL *****************************************************************************/
