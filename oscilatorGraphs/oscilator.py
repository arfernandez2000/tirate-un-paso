import sys
import matplotlib.pyplot as plt
import numpy as np

from draw import draw, drawAll, drawAllZoom
from parse import parseParameters


r, position, time, errorV = parseParameters(sys.argv[1])
r2, position2, time2, errorG = parseParameters(sys.argv[2])
r3, position3, time3, errorB = parseParameters(sys.argv[3])

drawAll([r, position, time], [r2, position2, time2], [r3, position3, time3])
drawAllZoom([r, position, time], [r2, position2, time2], [r3, position3, time3])
print("Error cuadratico Verlet: ", errorV)
print("Error cuadratico Gear: ", errorG)
print("Error cuadratico Beeman: ", errorB)
