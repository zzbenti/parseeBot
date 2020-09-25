from PIL import Image
import sys
img=Image.new("RGBA",(640,480),(int(sys.argv[1],16),int(sys.argv[2],16),int(sys.argv[3],16)))
img.save("setu.png")