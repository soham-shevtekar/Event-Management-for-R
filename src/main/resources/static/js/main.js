document.addEventListener('DOMContentLoaded', () => {
    // ==========================================
    // 1. ADVANCED LOADER (Fake Progress + Real Load)
    // ==========================================
    const loader = document.getElementById('loader');
    const progressBar = document.querySelector('.progress-bar');
    const progressText = document.getElementById('progress-text');
    
    let progress = 0;
    let isWindowLoaded = false;
    let loaderInterval;

    // Simulate progress 0-90%
    loaderInterval = setInterval(() => {
        if (progress < 90) {
            // slower increment as it gets higher
            const increment = progress < 50 ? Math.random() * 8 : Math.random() * 3;
            progress += increment;
            if (progress > 90) progress = 90;
            updateProgress(progress);
        }
    }, 50);

    function updateProgress(value) {
        const rounded = Math.floor(value);
        if (progressBar) progressBar.style.width = rounded + '%';
        if (progressText) progressText.innerText = rounded + '%';
    }

    // When the whole page (images, videos, etc.) is loaded
    window.onload = () => {
        isWindowLoaded = true;
        clearInterval(loaderInterval);
        
        // Rapidly fill to 100%
        let finalProgress = progress;
        const completeInterval = setInterval(() => {
            finalProgress += 4;
            if (finalProgress >= 100) {
                finalProgress = 100;
                updateProgress(finalProgress);
                clearInterval(completeInterval);
                
                // Add tiny delay before hiding for visual satisfaction
                setTimeout(() => {
                    if (loader) {
                        loader.style.opacity = '0';
                        loader.style.visibility = 'hidden';
                    }
                }, 400);
            } else {
                updateProgress(finalProgress);
            }
        }, 15);
    };

    // ==========================================
    // 2. NAVBAR SCROLL EFFECT
    // ==========================================
    const header = document.querySelector('header');
    
    window.addEventListener('scroll', () => {
        if (window.scrollY > 50) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    }, { passive: true });

    // ==========================================
    // 3. INTERSECTION OBSERVERS (Scroll Animations)
    // ==========================================
    const observerOptions = {
        root: null,
        rootMargin: '0px',
        threshold: 0.15
    };

    const animateOnScroll = new IntersectionObserver((entries, observer) => {
        entries.forEach((entry) => {
            if (entry.isIntersecting) {
                // Add visible class to trigger CSS transition
                entry.target.classList.add('visible');
                // Unobserve after animating once
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);

    // Observe specific elements
    const fadeUpElements = document.querySelectorAll('.event-card, .about-text, .about-image');
    fadeUpElements.forEach(el => animateOnScroll.observe(el));
    
    
    // ==========================================
    // 4. PARALLAX VIDEO SCROLL TUNING (Optional extra Polish)
    // ==========================================
    // The CSS fixed position handles the main effect, but we can dim the video slightly as we scroll down
    const parallaxVideo = document.querySelector('.parallax-bg');
    if (parallaxVideo) {
        window.addEventListener('scroll', () => {
            if (window.scrollY < window.innerHeight) {
                // Fades out the video slightly as you scroll down
                const opacity = 1 - (window.scrollY / window.innerHeight) * 0.5;
                parallaxVideo.style.opacity = opacity;
            }
        }, { passive: true });
    }

    // ==========================================
    // 5. AUTH MODAL LOGIC
    // ==========================================
    const authModal = document.getElementById('authModal');
    const openAuthModalBtn = document.getElementById('openAuthModalBtn');
    const closeAuthModal = document.getElementById('closeAuthModal');
    
    const signInContainer = document.getElementById('signInContainer');
    const signUpContainer = document.getElementById('signUpContainer');
    const showSignUpBtn = document.getElementById('showSignUp');
    const showSignInBtn = document.getElementById('showSignIn');

    if (openAuthModalBtn) {
        openAuthModalBtn.addEventListener('click', () => {
            authModal.classList.add('active');
        });
    }

    if (closeAuthModal) {
        closeAuthModal.addEventListener('click', () => {
            authModal.classList.remove('active');
        });
    }

    // Close on overlay click
    if (authModal) {
        authModal.addEventListener('click', (e) => {
            if (e.target === authModal) {
                authModal.classList.remove('active');
            }
        });
    }

    // Toggle forms
    if (showSignUpBtn) {
        showSignUpBtn.addEventListener('click', (e) => {
            e.preventDefault();
            signInContainer.style.display = 'none';
            signUpContainer.style.display = 'block';
        });
    }

    if (showSignInBtn) {
        showSignInBtn.addEventListener('click', (e) => {
            e.preventDefault();
            signUpContainer.style.display = 'none';
            signInContainer.style.display = 'block';
        });
    }
});
