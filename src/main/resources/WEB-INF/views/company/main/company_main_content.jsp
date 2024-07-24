<%@ page contentType="text/html;charset=UTF-8" language="java" %>

	<section class="py-4">
		<div class="container">
			<div class="row g-4">
				<div class="col-12">
					<div class="row g-4">
						<div class="col-sm-6 col-lg-3">
							<div class="card card-body border p-3">
								<div class="d-flex align-items-center">
									<div class="icon-xl fs-1 bg-dark bg-opacity-10 rounded-3 text-dark">
										<i class="bi bi-people-fill"></i>
									</div>
									<div class="ms-3">
										<h3>10명</h3>
										<h6 class="mb-0">지원자</h6>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-lg-3">
							<div class="card card-body border p-3">
								<div class="d-flex align-items-center">
									<div class="icon-xl fs-1 bg-primary bg-opacity-10 rounded-3 text-primary">
										<i class="bi bi-file-earmark-text-fill"></i>
									</div>
									<div class="ms-3">
										<h3>3개</h3>
										<h6 class="mb-0">공고</h6>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-6 col-lg-3">
							<div class="card card-body border p-3">
								<div class="d-flex align-items-center">
									<div class="icon-xl fs-1 bg-danger bg-opacity-10 rounded-3 text-danger">
										<i class="bi bi-suit-heart-fill"></i>
									</div>
									<div class="ms-3">
										<h3>100명</h3>
										<h6 class="mb-0">관심/스크랩</h6>
									</div>
								</div>
							</div>
						</div>

						<div class="col-sm-6 col-lg-3">
							<div class="card card-body border p-3">
								<div class="d-flex align-items-center">
									<div class="icon-xl fs-1 bg-success bg-opacity-10 rounded-3 text-success">
										<i class="bi bi-currency-dollar"></i>
									</div>
									<div class="ms-3">
										<h3>15만원</h3>
										<h6 class="mb-0">이번달 비용</h6>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-12">
					<div class="card border bg-transparent rounded-3">
						<div class="card-header bg-transparent border-bottom p-3">
							<div class="d-sm-flex justify-content-between align-items-center">
								<h5 class="mb-2 mb-sm-0">내 공고 <span id="jobCount" class="badge bg-primary bg-opacity-10 text-primary"></span></h5>
								<a href="/company/job_posting/registration.html" class="btn btn-sm btn-primary mb-0">Add New</a>
							</div>
						</div>
						<div class="card-body">
							<div class="row g-3 align-items-center justify-content-between mb-3">
								<div class="col-md-8">
									<form class="rounded position-relative" onsubmit="searchJobPostings(event)">
										<input id="searchInput" class="form-control pe-5 bg-transparent" type="search" placeholder="Search" aria-label="Search">
										<button class="btn bg-transparent border-0 px-2 py-0 position-absolute top-50 end-0 translate-middle-y" type="submit"><i class="fas fa-search fs-6 "></i></button>
									</form>
								</div>
								<div class="col-md-3">
									<form>
										<select id="categorySelect" class="form-select z-index-9 bg-transparent" aria-label=".form-select-sm">
											<option value="전체">전체</option>
											<option value="공고 제목">공고 제목</option>
											<option value="공고 타입">공고 타입</option>
											<option value="모집 분야">모집 분야</option>
											<option value="상태">상태</option>
										</select>
									</form>
								</div>
							</div>
							<div class="table-responsive border-0">
								<table class="table align-middle p-4 mb-0 table-hover table-shrink">
									<thead class="table-dark">
									<tr>
										<th scope="col" class="border-0 rounded-start">공고 제목</th>
										<th scope="col" class="border-0">공고 타입</th>
										<th scope="col" class="border-0">게시일</th>
										<th scope="col" class="border-0">모집 분야</th>
										<th scope="col" class="border-0">상태</th>
										<th scope="col" class="border-0 rounded-end">비고</th>
									</tr>
									</thead>
									<tbody class="border-top-0">
									</tbody>
								</table>
							</div>
							<div class="d-sm-flex justify-content-sm-between align-items-sm-center mt-4 mt-sm-3">
								<p id="totalEntries" class="mb-sm-0 text-center text-sm-start">총${jobPostings.length}</p>
								<nav class="mb-sm-0 d-flex justify-content-center" aria-label="navigation">
									<ul class="pagination pagination-sm pagination-bordered mb-0">
										<li class="page-item" id="prevPage">
											<a class="page-link" href="#" tabindex="-1" aria-disabled="true" onclick="changePage(currentPage - 1)">Prev</a>
										</li>
										<span id="paginationNumbers"></span>
										<li class="page-item" id="nextPage">
											<a class="page-link" href="#" onclick="changePage(currentPage + 1)">Next</a>
										</li>
									</ul>
								</nav>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xl-8">
					<div class="card border h-100">
						<div class="card-header p-3 border-bottom">
							<h5 class="card-header-title mb-0">Traffic stats</h5>
						</div>
						<div class="card-body">
							<div id="apexChartTrafficStats" class="mt-2"></div>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-xxl-4">
					<div class="card border h-100">
						<div class="card-header border-bottom p-3">
							<h5 class="card-header-title mb-0">Latest post</h5>
						</div>
						<div class="card-body p-3">
							<div class="row">
								<div class="col-12">
									<div class="d-flex align-items-center position-relative">
										<img class="w-60 rounded" src="assets/images/blog/1by1/01.jpg" alt="product">
										<div class="ms-3">
											<a href="#" class="h6 stretched-link">Dirty little secrets about the business industry</a>
											<p class="small mb-0">Jun 17, 2022</p>
										</div>
									</div>
								</div>
								<hr class="my-3">
								<div class="col-12">
									<div class="d-flex align-items-center position-relative">
										<img class="w-60 rounded" src="assets/images/blog/1by1/02.jpg" alt="product">
										<div class="ms-3">
											<a href="#" class="h6 stretched-link">12 worst types of business accounts you follow on Twitter</a>
											<p class="small mb-0">Nov 11, 2022</p>
										</div>
									</div>
								</div>
								<hr class="my-3">
								<div class="col-12">
									<div class="d-flex align-items-center position-relative">
										<img class="w-60 rounded" src="assets/images/blog/1by1/03.jpg" alt="product">
										<div class="ms-3">
											<a href="#" class="h6 stretched-link">Bad habits that people in the industry need to quit</a>
											<p class="small mb-0">Sep 01, 2022</p>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="card-footer border-top text-center p-3">
							<a href="#">View all Posts</a>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-xxl-4">
					<div class="card border h-100">
						<div class="card-header border-bottom p-3">
							<h5 class="card-header-title mb-0">Recent comments</h5>
						</div>
						<div class="card-body p-3">
							<div class="row">
								<div class="col-12">
									<div class="d-flex align-items-center position-relative">
										<div class="avatar avatar-lg flex-shrink-0">
											<img class="avatar-img rounded-2" src="assets/images/avatar/06.jpg" alt="avatar">
										</div>
										<div class="ms-3">
											<p class="mb-1"> <a class="h6 fw-normal stretched-link" href="#"> Supposing so be resolving breakfast am or perfectly.. </a></p>
											<div class="d-flex justify-content-between">
												<p class="small mb-0">by Joan</p>
											</div>
										</div>
									</div>
								</div>
								<hr class="my-3">
								<div class="col-12">
									<div class="d-flex align-items-center position-relative">
										<div class="avatar avatar-lg flex-shrink-0">
											<img class="avatar-img rounded-2" src="assets/images/avatar/08.jpg" alt="avatar">
										</div>
										<div class="ms-3">
											<p class="mb-1"> <a class="h6 fw-normal stretched-link" href="#"> We focus a great deal on the understanding of behavioral.. </a></p>
											<div class="d-flex justify-content-between">
												<p class="small mb-0">by Allen Smith</p>
											</div>
										</div>
									</div>
								</div>
								<hr class="my-3">
								<div class="col-12">
									<div class="d-flex align-items-center position-relative">
										<div class="avatar avatar-lg flex-shrink-0">
											<img class="avatar-img rounded-2" src="assets/images/avatar/04.jpg" alt="avatar">
										</div>
										<div class="ms-3">
											<p class="mb-1"> <a class="h6 fw-normal stretched-link" href="#"> Supposing so be resolving breakfast am or perfectly.. </a></p>
											<div class="d-flex justify-content-between">
												<p class="small mb-0">by Louis Ferguson</p>
											</div>
										</div>
									</div>
								</div>
								<hr class="my-3">
								<div class="col-12">
									<div class="d-flex align-items-center position-relative">
										<div class="avatar avatar-lg flex-shrink-0">
											<img class="avatar-img rounded-2" src="assets/images/avatar/05.jpg" alt="avatar">
										</div>
										<div class="ms-3">
											<p class="mb-1"> <a class="h6 fw-normal stretched-link" href="#"> Supposing so be resolving breakfast am or perfectly.. </a></p>
											<div class="d-flex justify-content-between">
												<p class="small mb-0">by Joan Wallace</p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-xxl-4">
					<div class="card border h-100">
						<div class="card-header border-bottom d-flex justify-content-between align-items-center  p-3">
							<h5 class="card-header-title mb-0">Notice board</h5>
							<div class="dropdown text-end">
								<a href="#" class="btn border-0 p-0 mb-0" role="button" id="dropdownShare3" data-bs-toggle="dropdown" aria-expanded="false">
									<i class="bi bi-three-dots fa-fw"></i>
								</a>
								<ul class="dropdown-menu dropdown-w-sm dropdown-menu-end min-w-auto shadow rounded" aria-labelledby="dropdownShare3">
									<li><a class="dropdown-item" href="#"><i class="bi bi-pencil-square fa-fw me-2"></i>Edit</a></li>
									<li><a class="dropdown-item" href="#"><i class="bi bi-trash fa-fw me-2"></i>Remove</a></li>
								</ul>
							</div>
						</div>
						<div class="card-body p-3">
							<div class="custom-scrollbar h-350">
								<div class="row">
									<div class="col-12">
										<div class="d-flex justify-content-between position-relative">
											<div class="d-sm-flex">
												<div class="icon-lg bg-warning bg-opacity-15 text-warning rounded-2 flex-shrink-0">
													<i class="fas fa-user-tie fs-5"></i>
												</div>
												<div class="ms-0 ms-sm-3 mt-2 mt-sm-0">
													<h6 class="mb-0"><a href="#" class="stretched-link">Join New Author</a></h6>
													<p class="mb-0">Amongst moments do in arrived Fat weddings believed prospect</p>
													<span class="small">5 min ago</span>
												</div>
											</div>
										</div>
									</div>
									<hr class="my-3">
									<div class="col-12">
										<div class="d-flex justify-content-between position-relative">
											<div class="d-sm-flex">
												<div class="icon-lg bg-success bg-opacity-10 text-success rounded-2 flex-shrink-0">
													<i class="bi bi-chat-left-quote-fill fs-5"></i>
												</div>
												<div class="ms-0 ms-sm-3 mt-2 mt-sm-0">
													<h6 class="mb-0"><a href="#" class="stretched-link">Add 5 New Blogs</a></h6>
													<p class="mb-0">Arrived Fat weddings believed prospect</p>
													<span class="small">4 hour ago</span>
												</div>
											</div>
										</div>
									</div>
									<hr class="my-3">
									<div class="col-12">
										<div class="d-flex justify-content-between position-relative">
											<div class="d-sm-flex">
												<div class="icon-lg bg-danger bg-opacity-10 text-danger rounded-2 flex-shrink-0">
													<i class="bi bi-bell-fill fs-5"></i>
												</div>
												<div class="ms-0 ms-sm-3 mt-2 mt-sm-0">
													<h6 class="mb-0"><a href="#" class="stretched-link">5 New Subscribers</a></h6>
													<p class="mb-0">Weddings believed prospect Arrived</p>
													<span class="small">4 hour ago</span>
												</div>
											</div>
										</div>
									</div>
									<hr class="my-3">
									<div class="col-12">
										<div class="d-flex justify-content-between position-relative">
											<div class="d-sm-flex">
												<div class="icon-lg bg-primary bg-opacity-10 text-primary rounded-2 flex-shrink-0"><i class="fas fa-globe fs-5"></i></div>
												<div class="ms-0 ms-sm-3 mt-2 mt-sm-0">
													<h6 class="mb-0"><a href="#" class="stretched-link">Update New Feature</a></h6>
													<span class="small">3 days ago</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="card-footer border-top text-center p-3">
							<a href="#">View all Notice List</a>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-xxl-4">
					<div class="card border h-100">
						<div class="card-header border-bottom d-flex justify-content-between align-items-center p-3">
							<h5 class="card-header-title mb-0">Traffic sources</h5>
							<a href="#" class="btn btn-sm btn-link p-0 mb-0 text-reset">View all</a>
						</div>
						<div class="card-body p-4">
							<div class=" mx-auto">
								<div id="apexChartTrafficSources"></div>
							</div>
							<ul class="list-inline text-center mt-3">
								<li class="list-inline-item pe-2"><i class="text-primary fas fa-circle pe-1"></i> Search </li>
								<li class="list-inline-item pe-2"><i class="text-success fas fa-circle pe-1"></i> Direct </li>
								<li class="list-inline-item pe-2"><i class="text-danger fas fa-circle pe-1"></i> Social </li>
								<li class="list-inline-item pe-2"><i class="text-warning fas fa-circle pe-1"></i> Display ads </li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
