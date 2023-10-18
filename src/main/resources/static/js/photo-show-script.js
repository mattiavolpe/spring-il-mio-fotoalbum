const imgEl = document.getElementById("single_image");
const zoomEl = document.getElementById("zoom_icon_wrapper");
const zoomIconEl = zoomEl.querySelector("i");

const overlayEl = document.getElementById("full_screen_overlay");

document.body.addEventListener("click", e => {
	if (e.target == imgEl || e.target == zoomEl || e.target == zoomIconEl) {
		overlayEl.classList.remove("d-none");
		overlayEl.classList.add("d-flex");
		document.body.append(overlayEl);		
	}
});

document.body.addEventListener("click", () => {
	overlayEl.classList.remove("d-flex");
	overlayEl.classList.add("d-none");
}, true);
