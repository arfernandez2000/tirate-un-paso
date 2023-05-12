from oscilatorUtils import calculate

def parseParameters(file):
    with open(file) as statesFile:
        statesLines = statesFile.readlines()

    r = []
    time = []
    position = []
    error = 0
    for line in statesLines:
        newline = line.strip()
        split = newline.split()
        if len(split) == 3:
            aux = calculate(float(split[0]))
            r.append(aux)
            position.append(float(split[1]))
            time.append(float(split[0]))

    for x, y in zip(r, position):
        error = error + (y - x) ** 2
    return r, position, time, error

def parseParametersErrors(file):
    with open(file) as statesFile:
        statesLines = statesFile.readlines()

    r = []
    time = []
    position = []
    error = 0
    for line in statesLines:
        newline = line.strip()
        split = newline.split()
        if len(split) == 3:
            aux = calculate(float(split[0]))
            r.append(aux)
            position.append(float(split[1]))
            time.append(float(split[0]))

    for x, y in zip(r, position):
        error = error + (y - x) ** 2
    return error
