import { useEffect, useState } from 'react';

export const useImagePreloader = (path: string, totalFrames: number) => {
  const [images, setImages] = useState<HTMLImageElement[]>([]);
  const [isLoaded, setIsLoaded] = useState(false);
  const [loadedCount, setLoadedCount] = useState(0);

  useEffect(() => {
    const loadedImages: HTMLImageElement[] = [];
    let count = 0;

    for (let i = 1; i <= totalFrames; i++) {
      const img = new Image();
      // Assumes naming like 0001.webp, 0002.webp etc.
      const frameIndex = i.toString().padStart(4, '0');
      img.src = `${path}/${frameIndex}.webp`; 
      
      img.onload = () => {
        count++;
        setLoadedCount(count);
        // We load somewhat asynchronously, but we place them in the correct index
        loadedImages[i - 1] = img;
        
        if (count === totalFrames) {
          setIsLoaded(true);
        }
      };
      
      img.onerror = () => {
        console.error(`Failed to load image: ${img.src}`);
        count++;
        setLoadedCount(count);
        if (count === totalFrames) {
          // Even if some fail, we move on
          setIsLoaded(true);
        }
      };
    }
    setImages(loadedImages);
  }, [path, totalFrames]);

  return { images, isLoaded, loadedCount };
};
