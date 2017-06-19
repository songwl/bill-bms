 var jsonData = "https://www.chinac.com/static/v3/particlesjs-register.json";
  jQuery(document).ready(function() {

      var swiper = new Swiper('.swiper-container.banner-swiper', {
          pagination: '.swiper-pagination',
          paginationClickable: true,
          nextButton: '.swiper-button-next',
          prevButton: '.swiper-button-prev',
          spaceBetween: 30,
          effect: 'fade',
          autoplay: '5000'
      });
    $(".demond-con dl").removeClass("active");
    $(".demond-con dl").on("mouseover",function(){
      $(".demond-con dl").removeClass("active");
      $(this).addClass("active");
    });
    $(".demond-con dl").on("mouseout",function(){
      $(".demond-con dl").removeClass("active");
    });
      $(".in_solution_con .hd ul li a").bind("click",function(){
          $(this).parent().addClass("on").siblings().removeClass("on");
          var showID = $(this).parent().index();
          slidersolution(showID);
      })
  });
    //解决方案切换
    var mySwiper = new Swiper('.in_solution_con .swiper-container',{});
    function slidersolution(showId){
        mySwiper.slideTo(showId, 1000, false);//切换到第一个slide，速度为1秒
    }
    particlesJS.load('jionbg', jsonData, function() {
        // console.log('particles.js loaded - callback');
    });