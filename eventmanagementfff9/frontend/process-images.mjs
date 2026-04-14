import fs from 'fs';
import path from 'path';
import sharp from 'sharp';

const sourceDir = 'C:\\Users\\soham\\Downloads\\eventimage';
const targetDir = 'C:\\Users\\soham\\OneDrive\\Desktop\\new workspace\\eventmanagementfff9\\frontend\\public\\eventimage';

if (!fs.existsSync(targetDir)) {
  fs.mkdirSync(targetDir, { recursive: true });
}

async function processImages() {
  const files = fs.readdirSync(sourceDir).filter(f => f.endsWith('.jpg') || f.endsWith('.jpeg') || f.endsWith('.png'));
  
  // Sort naturally to ensure correct sequential naming
  files.sort((a, b) => a.localeCompare(b, undefined, { numeric: true, sensitivity: 'base' }));

  console.log(`Found ${files.length} images. Processing...`);

  // To match the user's component `TOTAL_FRAMES = 120`, let's just process the first 120, or we can just process all of them
  // The user says "Your image folder eventimage appears to contain 240 frames." Wait, I'll process up to 120 frames to match the component, or maybe all of them? 
  // Let's process 120 frames. Wait, the user has 240. Maybe processing all 240 is better and we just set TOTAL_FRAMES=240 in the component. I will process all to be safe and update the component.
  
  for (let i = 0; i < files.length; i++) {
    const file = files[i];
    // Start index at 1, pad to 4 digits: 0001, 0002...
    const frameIndex = (i + 1).toString().padStart(4, '0');
    
    const sourcePath = path.join(sourceDir, file);
    const targetPath = path.join(targetDir, `${frameIndex}.webp`);

    try {
      await sharp(sourcePath)
        .webp({ quality: 70 })
        .toFile(targetPath);
      // log every 20th frame to not flood output
      if ((i + 1) % 20 === 0) {
        console.log(`Processed ${i + 1}/${files.length}`);
      }
    } catch (err) {
      console.error(`Error processing ${file}:`, err);
    }
  }

  console.log('Image processing complete! Total processed frames: ' + files.length);
}

processImages();
