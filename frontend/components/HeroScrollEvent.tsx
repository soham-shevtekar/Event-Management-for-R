"use client";
import React, { useRef, useEffect, useState } from 'react';
import { motion, useScroll, useTransform, useSpring } from 'framer-motion';
import { useImagePreloader } from '@/hooks/useImagePreloader';

const TOTAL_FRAMES = 240; // Updated to match the total converted frames

export const HeroScrollEvent = () => {
  const containerRef = useRef<HTMLDivElement>(null);
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const audioRef = useRef<HTMLAudioElement | null>(null);
  const [hasInteracted, setHasInteracted] = useState(false);
  
  // 1. Preload Images
  const { images, isLoaded, loadedCount } = useImagePreloader('/eventimage', TOTAL_FRAMES);

  // 2. Track Scroll Progress
  const { scrollYProgress } = useScroll({
    target: containerRef,
    offset: ["start start", "end end"]
  });

  // Smooth out the scroll progress for cinematic feel
  const smoothProgress = useSpring(scrollYProgress, { stiffness: 100, damping: 30, restDelta: 0.001 });

  // 3. Transform values for text animations
  const textOpacity = useTransform(smoothProgress, [0, 0.2, 0.4], [1, 1, 0]);
  const textY = useTransform(smoothProgress, [0, 0.4], [0, -100]);
  const canvasScale = useTransform(smoothProgress, [0, 1], [1, 1.1]);

  useEffect(() => {
    // Only initialize audio after a user interaction to respect browser autoplay policies
    const handleInteraction = () => {
      setHasInteracted(true);
      if (!audioRef.current) {
        const audio = new Audio('/ambient-crowd.mp3');
        audio.loop = true;
        audioRef.current = audio;
        audio.play().catch(e => console.warn('Audio play failed, possibly missing file or autoplay blocked:', e));
      }
    };
    
    document.addEventListener('click', handleInteraction, { once: true });
    return () => document.removeEventListener('click', handleInteraction);
  }, []);

  useEffect(() => {
    return smoothProgress.on("change", (v) => {
      if (audioRef.current) {
        audioRef.current.volume = Math.min(v * 2, 1);
      }
    });
  }, [smoothProgress]);

  // 4. Draw to Canvas
  useEffect(() => {
    if (!canvasRef.current || images.length === 0) return;

    const render = () => {
      const context = canvasRef.current?.getContext('2d');
      if (!context || !canvasRef.current) return;

      const frameIndex = Math.floor(smoothProgress.get() * (TOTAL_FRAMES - 1));
      const image = images[frameIndex];

      if (image && image.complete && image.naturalWidth !== 0) {
        const canvas = canvasRef.current;
        // Maintain Aspect Ratio (Cover effect)
        const canvasAspect = canvas.width / canvas.height;
        const imageAspect = image.width / image.height;
        let drawWidth, drawHeight, offsetX, offsetY;

        if (canvasAspect > imageAspect) {
          drawWidth = canvas.width;
          drawHeight = canvas.width / imageAspect;
          offsetX = 0;
          offsetY = (canvas.height - drawHeight) / 2;
        } else {
          drawHeight = canvas.height;
          drawWidth = canvas.height * imageAspect;
          offsetX = (canvas.width - drawWidth) / 2;
          offsetY = 0;
        }

        context.clearRect(0, 0, canvas.width, canvas.height);
        context.drawImage(image, offsetX, offsetY, drawWidth, drawHeight);
      }
    };

    const unsubscribe = smoothProgress.on("change", () => {
      requestAnimationFrame(render);
    });

    // Initial Draw if at least some images exist
    if (isLoaded || loadedCount > 0) {
      render();
    }

    return () => unsubscribe();
  }, [isLoaded, loadedCount, images, smoothProgress]);

  // Resize handler
  useEffect(() => {
    const handleResize = () => {
      if (canvasRef.current) {
        canvasRef.current.width = window.innerWidth;
        canvasRef.current.height = window.innerHeight;
      }
    };
    window.addEventListener('resize', handleResize);
    handleResize();
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  return (
    <div ref={containerRef} className="relative h-[300vh] bg-black">
      {/* Loading Overlay */}
      {!isLoaded && (
        <div className="fixed inset-0 z-50 flex flex-col items-center justify-center bg-black">
          <div className="w-64 h-2 bg-gray-800 rounded-full overflow-hidden mb-4">
            <div 
              className="h-full bg-teal-400 transition-all duration-300"
              style={{ width: `${(loadedCount / TOTAL_FRAMES) * 100}%` }}
            />
          </div>
          <p className="text-white font-mono uppercase tracking-widest text-sm">
            Optimizing Assets ... {Math.round((loadedCount / TOTAL_FRAMES) * 100)}%
          </p>
        </div>
      )}

      {/* Sticky Canvas Section */}
      <div className="sticky top-0 h-screen w-full overflow-hidden">
        <motion.canvas
          ref={canvasRef}
          style={{ scale: canvasScale, willChange: 'transform' }}
          className="absolute inset-0 h-full w-full object-cover bg-black"
        />
        
        {/* Cinematic Overlay */}
        <div className="absolute inset-0 bg-black/40 z-10 pointer-events-none" />

        {/* Content Overlay */}
        <motion.div 
          style={{ opacity: textOpacity, y: textY }}
          className="relative z-20 flex h-full flex-col items-center justify-center text-center px-4"
        >
          <motion.h1 
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 1 }}
            className="text-5xl md:text-7xl font-bold text-white drop-shadow-2xl"
          >
            Welcome to the Best <br />
            <span className="text-teal-400">Event Management</span> Platform
          </motion.h1>
          
          <motion.p 
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ delay: 0.5, duration: 1 }}
            className="mt-6 text-xl text-gray-200 max-w-2xl"
          >
            Manage, Plan, and Enjoy Your Events Seamlessly
          </motion.p>

          <motion.button
            whileHover={{ scale: 1.05, boxShadow: "0px 0px 20px rgba(45, 212, 191, 0.5)" }}
            whileTap={{ scale: 0.95 }}
            className="mt-10 px-8 py-4 bg-teal-400 text-black font-bold flex items-center gap-2 rounded-lg transition-all"
          >
            Discover More
            {/* Optional subtle animation icon could go here */}
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M5 12h14"/><path d="m12 5 7 7-7 7"/></svg>
          </motion.button>
        </motion.div>
      </div>
      
      {/* Spacer for scroll depth */}
      <div className="h-[100vh]" />
    </div>
  );
};
