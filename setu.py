from PIL import Image
import sys
img=Image.new("RGBA",(640,480),(int(sys.argv[1]),int(sys.argv[2]),int(sys.argv[3])))
img.save("setu.png")