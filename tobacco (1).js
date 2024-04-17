document.addEventListener("DOMContentLoaded", function() {
    const homeLink = document.getElementById("home-link");
    const tobaccoLink = document.getElementById("tobacco-link");
    const aboutLink = document.getElementById("about-link");
    const contactLink = document.getElementById("contact-link");
    const productsSection = document.querySelector(".products");
    const aboutSection = document.createElement("section");
    const contactSection = document.createElement("section");
    const cartItemsList = document.querySelector(".cart-items");
    const cartTotal = document.querySelector(".total");
    const clearCartButton = document.querySelector(".clear-cart");
    const addToCartButtons = document.querySelectorAll(".add-to-cart");

    aboutSection.innerHTML = "<h2>За нас</h2><p>Нашата компания е основана през 2020 година и се занимава с продажбата на висококачествени тютюнови продукти.</p>";
    contactSection.innerHTML = "<h2>Контакти</h2><p>За връзка с нас, моля изпратете имейл на: info@tobaccoshop.com</p>";

    homeLink.addEventListener("click", function(event) {
        event.preventDefault();
        productsSection.style.display = "block";
        aboutSection.style.display = "none";
        contactSection.style.display = "none";
    });

    tobaccoLink.addEventListener("click", function(event) {
        event.preventDefault();
        productsSection.style.display = "block";
        aboutSection.style.display = "none";
        contactSection.style.display = "none";
    });
	
    aboutLink.addEventListener("click", function(event) {
		event.preventDefault();
		productsSection.style.display = "none";
		aboutSection.style.display = "block";
		contactSection.style.display = "none";
		aboutSection.innerHTML = "<h2>За нас</h2><p>Основано през 2023 година. Възможно най-качествен и разнообразен тютюн за Вас и вашите близки!</p>";
		aboutSection.scrollIntoView({ behavior: 'smooth' }); // Добавяме скролиране до секцията "За Нас"
	});

	contactLink.addEventListener("click", function(event) {
		event.preventDefault();
		productsSection.style.display = "none";
		aboutSection.style.display = "none";
		contactSection.style.display = "block";
		contactSection.scrollIntoView({ behavior: 'smooth' }); // Добавяме скролиране до секцията "Контакти"
	});


    // Обект, където ще съхраняваме информация за продуктите в количката
    const cartItems = {};

    // Функционалност за добавяне на продукт в количката
    addToCartButtons.forEach(button => {
        button.addEventListener("click", function() {
            const product = this.parentElement;
            const productName = product.querySelector("h2").textContent;
            const productPrice = parseFloat(product.querySelector(".price").textContent.slice(1));

            // Актуализиране на броя на добавените продукти от определен вид
            if (cartItems[productName]) {
                cartItems[productName].quantity++;
            } else {
                cartItems[productName] = {
                    price: productPrice,
                    quantity: 1
                };
            }

            updateCart();
        });
    });

    // Функционалност за премахване на продукт от количката
    cartItemsList.addEventListener("click", function(event) {
        if (event.target.tagName === "LI") {
            const productName = event.target.textContent.split(" - ")[0];
            cartItems[productName].quantity--;
            if (cartItems[productName].quantity === 0) {
                delete cartItems[productName];
            }
            updateCart();
        }
    });

    // Функционалност за изчистване на цялата количка
    clearCartButton.addEventListener("click", function() {
        for (let productName in cartItems) {
            delete cartItems[productName];
        }
        updateCart();
    });

    // Функция за актуализиране на количката
    function updateCart() {
        cartItemsList.innerHTML = "";
        let total = 0;
        for (let productName in cartItems) {
            const { price, quantity } = cartItems[productName];
            const li = document.createElement("li");
            li.textContent = `${productName} - $${price} x ${quantity}`;
            cartItemsList.appendChild(li);
            total += price * quantity;
        }
        cartTotal.textContent = `$${total.toFixed(2)}`;
    }
});