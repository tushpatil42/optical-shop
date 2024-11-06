// Initialize cart from localStorage or start fresh
let cart = JSON.parse(localStorage.getItem("cart")) || [];

// Products array
const products = [
   {
      id: 21,
      name: "Stylish Glasses",
      price: 1500,
      image:
         "https://images.unsplash.com/photo-1524255684952-d7185b509571?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
      description: "High quality, stylish glasses for everyday wear."
   },
   {
      id: 22,
      name: "Blue Light Glasses",
      price: 1800,
      image:
         "https://images.unsplash.com/photo-1618677366898-d33b947dce67?q=80&w=1886&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
      description:
         "Protect your eyes from harmful blue light with these modern glasses."
   },
   {
      id: 23,
      name: "Prescription Glasses",
      price: 2000,
      image:
         "https://images.pexels.com/photos/2084810/pexels-photo-2084810.jpeg?auto=compress&cs=tinysrgb&w=600",
      description:
         "Prescription glasses with a variety of lenses to suit your needs."
   }
];

// Format price in INR
function formatPrice(price) {
   return "₹" + price.toLocaleString("en-IN", { minimumFractionDigits: 2 });
}

// Add product to cart
function addToCart(productId) {
   const product = products.find((item) => item.id === productId);

   if (!product) {
      console.error("Product not found");
      return;
   }

   const existingProduct = cart.find((item) => item.id === productId);

   if (existingProduct) {
      existingProduct.quantity += 1;
   } else {
      cart.push({ ...product, quantity: 1 });
   }

   updateCartUI();
   localStorage.setItem("cart", JSON.stringify(cart));

   // Add animation for feedback
   const cartCount = document.getElementById("cart-count");
   cartCount.classList.add("bump");
   setTimeout(() => cartCount.classList.remove("bump"), 300);
}

// Remove product from cart
function removeFromCart(productId) {
   cart = cart.filter((item) => item.id !== productId);
   updateCartUI();
   localStorage.setItem("cart", JSON.stringify(cart));
}

// Update the cart UI dynamically
function updateCartUI() {
   const cartContainer = document.getElementById("cart-items");
   const cartItemCount = document.getElementById("cart-count");
   const totalPriceElement = document.getElementById("total-price");
   const cartSection = document.getElementById("cart-modal");

   // Clear the cart container
   cartContainer.innerHTML = "";

   let total = 0;

   if (cart.length === 0) {
      cartContainer.innerHTML = "<p>Your cart is empty.</p>";
   } else {
      // Populate the cart items
      cart.forEach((item) => {
         const itemTotal = item.price * item.quantity;
         total += itemTotal;

         const cartItem = document.createElement("div");
         cartItem.classList.add("cart-item");

         cartItem.innerHTML = `
        <img src="${item.image}" alt="${item.name}" class="cart-item-img">
        <div class="cart-item-details">
          <h4>${item.name}</h4>
          <p>Quantity: ${item.quantity}</p>
          <p>Price: ${formatPrice(item.price)}</p>
          <p>Total: ${formatPrice(itemTotal)}</p>
        </div>
        <button class="remove-btn" onclick="removeFromCart(${
           item.id
        })">Remove</button>
      `;

         cartContainer.appendChild(cartItem);
      });
   }

   cartItemCount.innerText = cart.reduce((acc, item) => acc + item.quantity, 0);
   totalPriceElement.innerText = formatPrice(total);
}

// Open cart modal
function openCart() {
   const cartModal = document.getElementById("cart-modal");
   cartModal.style.display = "block";
}

// Close cart modal
function closeCart() {
   const cartModal = document.getElementById("cart-modal");
   cartModal.style.display = "none";
}

// Open product modal for more details
function openProductModal(productId) {
   const product = products.find((item) => item.id === productId);
   const modal = document.getElementById("product-modal");
   const modalBody = modal.querySelector(".modal-body");

   modalBody.innerHTML = `
    <h2>${product.name}</h2>
    <img src="${product.image}" alt="${product.name}" class="modal-img">
    <p>${product.description}</p>
    <p class="price">${formatPrice(product.price)}</p>
    <div class="modal-buttons">
      <button class="btn" onclick="addToCart(${
         product.id
      })">Add to Cart</button>
      <button class="btn close-btn" onclick="closeModal()">Close</button>
    </div>
  `;

   modal.style.display = "block";
}

// Close product modal
function closeModal() {
   const modal = document.getElementById("product-modal");
   modal.style.display = "none";
}

// Smooth scrolling for anchor links
document.querySelectorAll('a[href^="#"]').forEach((anchor) => {
   anchor.addEventListener("click", function (e) {
      if (this.getAttribute("href") === "#cart") {
         e.preventDefault();
         openCart();
         return;
      }

      e.preventDefault();
      document.querySelector(this.getAttribute("href")).scrollIntoView({
         behavior: "smooth"
      });
   });
});

// Populate products on page
function populateProducts() {
   const productList = document.getElementById("product-list");

   products.forEach((product) => {
      const productCard = document.createElement("div");
      productCard.classList.add("product-card");

      productCard.innerHTML = `
      <img src="${product.image}" alt="${product.name}">
      <h3>${product.name}</h3>
      <p class="price">${formatPrice(product.price)}</p>
      <div class="product-buttons">
        <button class="btn" onclick="addToCart(${
           product.id
        })">Add to Cart</button>
        <button class="btn" onclick="openProductModal(${
           product.id
        })">View Details</button>
      </div>
    `;

      productList.appendChild(productCard);
   });
}

// Close modals when clicking outside of them
window.onclick = function (event) {
   const cartModal = document.getElementById("cart-modal");
   const productModal = document.getElementById("product-modal");

   if (event.target === cartModal) {
      cartModal.style.display = "none";
   }

   if (event.target === productModal) {
      productModal.style.display = "none";
   }
};

// Initial UI update on page load
document.addEventListener("DOMContentLoaded", () => {
   updateCartUI(); // Update cart when the page loads
   populateProducts(); // Populate product list when the page loads
});
