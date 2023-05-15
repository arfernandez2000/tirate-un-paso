import sys
import matplotlib.pyplot as plt
import numpy as np

from parse import parseParametersErrors
from draw import drawErrors


deltas = [10**-2, 10**-3, 10**-4, 10**-5, 10**-6]
eVerlet2 = parseParametersErrors(sys.argv[1])
eVerlet3 = parseParametersErrors(sys.argv[2])
eVerlet4 = parseParametersErrors(sys.argv[3])
eVerlet5 = parseParametersErrors(sys.argv[4])
eVerlet6 = parseParametersErrors(sys.argv[5])

eGear2 = parseParametersErrors(sys.argv[6])
eGear3 = parseParametersErrors(sys.argv[7])
eGear4 = parseParametersErrors(sys.argv[8])
eGear5 = parseParametersErrors(sys.argv[9])
eGear6 = parseParametersErrors(sys.argv[10])

eBeeman2 = parseParametersErrors(sys.argv[11])
eBeeman3 = parseParametersErrors(sys.argv[12])
eBeeman4 = parseParametersErrors(sys.argv[13])
eBeeman5 = parseParametersErrors(sys.argv[14])
eBeeman6 = parseParametersErrors(sys.argv[15])

eVerlet = [eVerlet2, eVerlet3, eVerlet4, eVerlet5, eVerlet6]
eGear = [eGear2, eGear3, eGear4, eGear5, eGear6]
errorsB = [eBeeman2, eBeeman3, eBeeman4, eBeeman5, eBeeman6]
drawErrors(deltas, eVerlet, eGear, errorsB)
