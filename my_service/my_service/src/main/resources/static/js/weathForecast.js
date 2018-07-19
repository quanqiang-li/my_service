/**
 * 天气预报
 */

$(document)
		.ready(
				function($) {

					/**
					 * 最新天气预报情况
					 * 
					 * @returns
					 */
					function latestDays() {
						var location = $("#location").val();
						if (location == "") {
							$("#weathForecast").text("请输入地区名称");
							return;
						}
						//出现滚动条
						$("#bar-active").show();
						// 是否命中sessionStorage
						if (sessionStorageHit(location)) {
							console.log("从缓存获取数据");
							return;
						}
						console.log("从服务端获取数据");
						$.ajax({
									url : "/weathForecast/latestDays",
									type : "get",
									data : {
										"location" : location
									},
									cache : true,
									dataType : "json",
									success : function(data) {
										var weatherForecastDays = data.dataBody.weatherForecastDays;
										// 异常直接结束
										if (data.errorCode != 0) {
											$("#weathForecast").text(
													weatherForecastDays.status);
											return;
										}
										// 渲染模版
										var html = template(
												'weathForecast_art',
												weatherForecastDays);
										$("#weathForecast").html(html);
										// sessionStorage存储，只能是字符串,当天
										var weatherForecastStr = JSON
												.stringify(weatherForecastDays);
										sessionStorage.setItem("latestDays-" + location + "-" + getDay(), weatherForecastStr);
									},
									error : function(data) {
										$("#weathForecast").text(data);
									},
									complete:function(data){
										//隐藏滚动条
										$("#bar-active").hide();
									}
								});
					}
					/**
					 * 是否命中sessionStorage
					 * 
					 * @param location
					 * @returns
					 */
					function sessionStorageHit(location) {
						var weatherForecastStr = sessionStorage
								.getItem("latestDays-" + location + "-" + getDay());
						if (weatherForecastStr == null) {
							return false;
						}
						var weatherForecastDays = JSON
								.parse(weatherForecastStr)
						// 渲染模版
						var html = template('weathForecast_art',
								weatherForecastDays);
						//隐藏滚动条
						$("#bar-active").hide();
						$("#weathForecast").html(html);
						return true;
					}
					
					function getDay(){
						var myDate = new Date();//获取系统当前时间
						var month = myDate.getMonth(); //获取当前月份(0-11,0代表1月)
						var date = myDate.getDate(); //获取当前日(1-31)
						return month +"-" +date;
						
					}

					$("#find").bind("click", latestDays);
					//回车事件，抬起按键触发
					$(document).keyup(function(event){
						  if(event.keyCode ==13){
						    $("#find").trigger("click");
						  }
					});
					
					
					
					/*跨域访问*/
					
					/**
					 * 最新天气预报情况
					 * 
					 * @returns
					 */
					function latestDaysForOrigin() {
						var location = $("#location").val();
						if (location == "") {
							$("#weathForecast").text("请输入地区名称");
							return;
						}
						//出现滚动条
						$("#bar-active").show();
						// 是否命中sessionStorage
						if (sessionStorageHit(location)) {
							return;
						}
						$.ajax({
									url : "http://172.16.16.33:8787/weathForecast/latestDaysForOrigin",
									type : "get",
									dataType:"jsonp",
									jsonp:"callback",
									data : {
										"location" : location
									},
									cache : true,								
									success : function(data) {
										var weatherForecastDays = data.dataBody.weatherForecastDays;
										// 异常直接结束
										if (data.errorCode != 0) {
											$("#weathForecast").text(
													weatherForecastDays.status);
											return;
										}
										// 渲染模版
										var html = template(
												'weathForecast_art',
												weatherForecastDays);
										$("#weathForecast").html(html);
										// sessionStorage存储，只能是字符串,当天
										var weatherForecastStr = JSON
												.stringify(weatherForecastDays);
										sessionStorage.setItem("latestDays-" + location + "-" + getDay(), weatherForecastStr);
									},
									error : function(data) {
										$("#weathForecast").text(data);
									},
									complete:function(data){
										//隐藏滚动条
										$("#bar-active").hide();
									}
								});
					}
					

				});