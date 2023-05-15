import sys
import matplotlib.pyplot as plt
import numpy as np

A = 1.0
m = 70.0
k = 10000
gamma = 100.0

def calculate(t):
    return A * (np.exp(-(gamma / (2 * m)) * t)) * (np.cos(np.power((k / m) - (gamma * gamma / (4 * (m * m))), 0.5) * t))
