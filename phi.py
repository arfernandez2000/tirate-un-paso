import matplotlib.pyplot as plt
import numpy as np
import math
import sys
import matplotlib as mpl

x1 = []
y1 = []
rx1 = []
ry1 = []

obs = open(sys.argv[1], "r")
for l in obs:
    aux_rx = []
    aux_ry = []
    balls = int(l)
    x1.append(round(float(next(obs)), 3))
    for i in range(0, balls):
        line = next(obs).split()
        aux_rx.append(float(line[1]))
        aux_ry.append(float(line[2]))
    next(obs)
    next(obs)
    rx1.append(aux_rx)
    ry1.append(aux_ry)
    # y1.append(np.mean(iter))
    # error1.append(np.std(iter))

rx2 = []
ry2 =[]

obs = open(sys.argv[2], "r")
for l in obs:
    aux_rx = []
    aux_ry = []
    balls = int(l)
    next(obs)
    for i in range(0, balls):
        line = next(obs).split()
        aux_rx.append(float(line[1]))
        aux_ry.append(float(line[2]))
    next(obs)
    next(obs)
    rx2.append(aux_rx)
    ry2.append(aux_ry)
    # y2.append(np.mean(iter))
    # error2.append(np.std(iter))

for i in range(0, len(x1)):
    sum = 0
    for j in range(0, len(rx1[i])):
        sum += math.sqrt((rx1[i][j] - rx2[i][j])**2 + (ry1[i][j] - ry2[i][j])**2)
    y1.append(sum)

x2 = []
y2 = []
rx1 = []
ry1 = []

obs = open(sys.argv[3], "r")
for l in obs:
    aux_rx = []
    aux_ry = []
    balls = int(l)
    x2.append(round(float(next(obs)), 4))
    for i in range(0, balls):
        line = next(obs).split()
        aux_rx.append(float(line[1]))
        aux_ry.append(float(line[2]))
    next(obs)
    next(obs)
    rx1.append(aux_rx)
    ry1.append(aux_ry)
    # y1.append(np.mean(iter))
    # error1.append(np.std(iter))

rx2 = []
ry2 =[]

obs = open(sys.argv[4], "r")
for l in obs:
    aux_rx = []
    aux_ry = []
    balls = int(l)
    next(obs)
    for i in range(0, balls):
        line = next(obs).split()
        aux_rx.append(float(line[1]))
        aux_ry.append(float(line[2]))
    next(obs)
    next(obs)
    rx2.append(aux_rx)
    ry2.append(aux_ry)
    # y2.append(np.mean(iter))
    # error2.append(np.std(iter))

for i in range(0, len(x2)):
    sum = 0
    for j in range(0, len(rx1[i])):
        sum += math.sqrt((rx1[i][j] - rx2[i][j])**2 + (ry1[i][j] - ry2[i][j])**2)
    y2.append(sum)

x3 = []
y3 = []
rx1 = []
ry1 = []

obs = open(sys.argv[5], "r")
for l in obs:
    aux_rx = []
    aux_ry = []
    balls = int(l)
    x3.append(round(float(next(obs)), 4))
    for i in range(0, balls):
        line = next(obs).split()
        aux_rx.append(float(line[1]))
        aux_ry.append(float(line[2]))
    next(obs)
    next(obs)
    rx1.append(aux_rx)
    ry1.append(aux_ry)
    # y1.append(np.mean(iter))
    # error1.append(np.std(iter))

rx2 = []
ry2 =[]

obs = open(sys.argv[6], "r")
for l in obs:
    aux_rx = []
    aux_ry = []
    balls = int(l)
    next(obs)
    for i in range(0, balls):
        line = next(obs).split()
        aux_rx.append(float(line[1]))
        aux_ry.append(float(line[2]))
    next(obs)
    next(obs)
    rx2.append(aux_rx)
    ry2.append(aux_ry)
    # y2.append(np.mean(iter))
    # error2.append(np.std(iter))

for i in range(0, len(x3)):
    sum = 0
    for j in range(0, len(rx1[i])):
        sum += math.sqrt((rx1[i][j] - rx2[i][j])**2 + (ry1[i][j] - ry2[i][j])**2)
    y3.append(sum)

x4 = []
y4 = []
rx1 = []
ry1 = []

obs = open(sys.argv[7], "r")
for l in obs:
    aux_rx = []
    aux_ry = []
    balls = int(l)
    x4.append(round(float(next(obs)), 4))
    for i in range(0, balls):
        line = next(obs).split()
        aux_rx.append(float(line[1]))
        aux_ry.append(float(line[2]))
    next(obs)
    next(obs)
    rx1.append(aux_rx)
    ry1.append(aux_ry)
    # y1.append(np.mean(iter))
    # error1.append(np.std(iter))

rx2 = []
ry2 =[]

obs = open(sys.argv[8], "r")
for l in obs:
    aux_rx = []
    aux_ry = []
    balls = int(l)
    next(obs)
    for i in range(0, balls):
        line = next(obs).split()
        aux_rx.append(float(line[1]))
        aux_ry.append(float(line[2]))
    next(obs)
    next(obs)
    rx2.append(aux_rx)
    ry2.append(aux_ry)
    # y2.append(np.mean(iter))
    # error2.append(np.std(iter))

for i in range(0, len(x4)):
    sum = 0
    for j in range(0, len(rx1[i])):
        sum += math.sqrt((rx1[i][j] - rx2[i][j])**2 + (ry1[i][j] - ry2[i][j])**2)
    y4.append(sum)


fig, ax = plt.subplots()
ax.set_yscale('log')
mpl.rcParams['savefig.transparent'] = True

ax.plot(x4, y4, label = "Φ⁵", color = "#f673a9")
ax.plot(x3, y3, label = "Φ⁴", color = "#7ec8d4")
ax.plot(x2, y2, label = "Φ³", color = "#06467e")
ax.plot(x1, y1, label = "Φ²", color = "#9a4496")

plt.ylabel("Φ (cm)")
plt.xlabel("Tiempo (s)")
plt.legend()
plt.show()

