import matplotlib.pyplot as plt
import numpy as np
import sys
import matplotlib as mpl

x = []
y = []
error = []
for i in range(1, len(sys.argv)):
    iter = []
    velocity = open(sys.argv[i], "r")
    x.append(float(next(velocity)))
    for line in velocity:
        aux = float(line)
        if(aux < 5000):
            iter.append(aux)
    print(len(iter))
    y.append(np.mean(iter))
    error.append(np.std(iter))

print(x)
print(y)
print(error)

fig, ax = plt.subplots()
mpl.rcParams['savefig.transparent'] = True
ax.errorbar(x, y, error, fmt='o', linewidth=2, capsize=6, color= "#6aa4d6")

plt.ylabel("Tiempo de finalización (s)")
plt.xlabel("Posición inicial de la bola blanca en y (cm)")
plt.show()