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
						var location = $("input[name='location']").val();
						if (location == "") {
							$("#weathForecast").text("请输入地区名称");
							return;
						}
						// 是否命中sessionStorage
						if (sessionStorageHit(location)) {
							return;
						}
						$
								.ajax({
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
										// sessionStorage存储，只能是字符串
										var weatherForecastStr = JSON
												.stringify(weatherForecastDays);
										sessionStorage.setItem("latestDays-"
												+ location, weatherForecastStr);
									},
									error : function(data) {
										$("#weathForecast").text(data);
										console.log(data);
									}
								})
					}
					/**
					 * 是否命中sessionStorage
					 * 
					 * @param location
					 * @returns
					 */
					function sessionStorageHit(location) {
						var weatherForecastStr = sessionStorage
								.getItem("latestDays-" + location);
						if (weatherForecastStr == null) {
							return false;
						}
						var weatherForecastDays = JSON
								.parse(weatherForecastStr)
						// 渲染模版
						var html = template('weathForecast_art',
								weatherForecastDays);
						$("#weathForecast").html(html);
						return true;
					}

					$("#find").bind("click", latestDays);

				})