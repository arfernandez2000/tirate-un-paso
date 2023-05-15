import sys
import matplotlib.pyplot as plt
import matplotlib as mpl
import numpy as np

from oscilatorUtils import calculate

def drawAll(verlet, gear, beeman):
    positionsVerlet = np.array(verlet[1])
    timesVerlet = np.array(verlet[2])
    positionsBeeman = np.array(beeman[1])
    timesBeeman = np.array(beeman[2])
    positionsBeeman = np.array(beeman[1])
    timesBeeman = np.array(beeman[2])
    positionsGear = np.array(gear[1])
    timesGear = np.array(gear[2])
    real_positions = np.array(verlet[0])


    mpl.rcParams['savefig.transparent'] = True

    plt.plot(timesVerlet, positionsVerlet, color="#9a4496")
    plt.plot(timesBeeman, positionsBeeman, color="#7ec8d4")
    plt.plot(timesGear, positionsGear, color="#06467e")
    plt.plot(timesVerlet, real_positions, color="#f673a9")
    plt.xlabel("Tiempo (s)")
    plt.ylabel("Posición (m)")
    plt.legend(["Verlet", "Beeman", "Gear", "Analitico"])
    plt.show()

def drawAllZoom(verlet, gear, beeman):

    positionsVerlet = np.array(verlet[1])
    timesVerlet = np.array(verlet[2])
    positionsBeeman = np.array(beeman[1])
    timesBeeman = np.array(beeman[2])
    positionsGear = np.array(gear[1])
    timesGear = np.array(gear[2])
    real_positions = np.array(verlet[0])

    plt.plot(timesVerlet, positionsVerlet, color="#9a4496")
    plt.plot(timesBeeman, positionsBeeman, color="#7ec8d4")
    plt.plot(timesGear, positionsGear, color="#06467e")
    plt.plot(timesVerlet, real_positions, color="#f673a9")
    plt.xlabel("Tiempo (s)")
    plt.ylabel("Posición (m)")
    plt.legend(["Verlet", "Beeman", "Gear", "Analitico"])
    delta = 1e-2
    to = 3.1545
    plt.xlim(to, to + delta)
    y = (calculate(to), calculate(to + delta))
    plt.ylim(min(y), max(y))
    plt.show()

def draw(data):
    positions = np.array(data[1])
    times = np.array(data[2])
    real_positions = np.array(data[0])

    plt.plot(times, positions)
    plt.plot(times, real_positions)
    plt.xlabel("Tiempo (s)")
    plt.ylabel("Posicion (m)")
    plt.legend(["Verlet", "Analitico"])
    plt.show()

def drawErrors(deltas, eVerlet, eGear, eBeeman):

    mpl.rcParams['savefig.transparent'] = True

    plt.plot(deltas, eVerlet, "o-", label="Verlet", color="#9a4496")
    plt.plot(deltas, eBeeman, "o-", label="Beeman", color="#7ec8d4")
    plt.plot(deltas, eGear, "o-", label="Gear", color="#06467e")


    plt.yscale("log")
    plt.xscale("log")
    plt.legend()
    plt.ylabel("Error cuadrático medio (m²)")
    plt.xlabel("Δt (s)")
    plt.show()