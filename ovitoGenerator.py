import sys

import numpy as np
import pandas as pd
from ovito.data import DataCollection, SimulationCell, Particles
from ovito.io import export_file
from ovito.pipeline import StaticSource, Pipeline


def export_to_ovito(frame_file):
    color_dictionary = {}
    data_frame = get_particle_data(frame_file, color_dictionary)

    pipeline = Pipeline(source=StaticSource(data=DataCollection()))

    def simulation_cell(frame, data):
        cell = SimulationCell()
        cell[:, 0] = (224, 0, 0)  # va el L donde estan los 7s
        cell[:, 1] = (0, 112, 0)
        cell[:, 2] = (0, 0, 56)
        data.objects.append(cell)

        particles = get_particles(data_frame[frame], color_dictionary)
        data.objects.append(particles)

    pipeline.modifiers.append(simulation_cell)

    export_file(pipeline, 'results_ovito46_2.dump', 'lammps/dump',
                columns=["Particle Identifier", "Position.X", "Position.Y", "Position.Z", "Radius", "Color.R",
                         "Color.G", "Color.B"],
                multiple_frames=True, start_frame=0, end_frame=len(data_frame) - 1)


ball_color_arr = [
    (255, 255, 255),
    (0, 255, 255),
    (127, 255, 212),
    (69, 139, 116),
    (227, 207, 87),
    (0, 0, 255),
    (138, 43, 226),
    (165, 42, 42),
    (255, 97, 3),
    (127, 255, 0),
    (255, 114, 86),
    (255, 20, 147),
    (255, 187, 255),
    (255, 255, 0),
    (255, 0, 0),
    (139, 131, 120),
]


def normalize_tuple_for_ovito(number: float):
    return number / 255


def id_to_color(ball_id: int) -> tuple:
    if ball_id == 0:
        return 255, 255, 255
    if 16 <= ball_id <= 21:
        return 0, 0, 0
    to_return = map(normalize_tuple_for_ovito, ball_color_arr[ball_id])
    return tuple(to_return)


def get_particle_data(frame_file, color_dictionary: dict):
    frames = []
    with open(frame_file, "r") as frame:
        next(frame)
        next(frame)
        next(frame)
        frame_lines = []
        iteration = 0
        for line in frame:
            ll = line.split()
            line_info = []
            # print(ll)
            for i, value in enumerate(ll):
                if len(ll) == 1 or (i != 3 and i != 4 and i != 6):
                    line_info.append(float(value))
            if len(ll) > 1:
                line_info.insert(3, 0)
                if color_dictionary.get(iteration) is None:
                    color_dictionary[iteration] = []
                color_rgb = id_to_color(int(line_info[0]))
                color_dictionary[iteration].append(color_rgb)
            if len(ll) == 1:
                iteration += 1
            if len(line_info) > 1:
                frame_lines.append(line_info)
            elif len(line_info) == 1 and line != '\n':
                next(frame)
                df = pd.DataFrame(frame_lines, columns=["id", "x", "y", "z", "radius"])
                frames.append(df)
                frame_lines = []
        df = pd.DataFrame(frame_lines, columns=["id", "x", "y", "z", "radius"])
        frames.append(df)
    # print(frames)
    # print(iteration)

    return frames


color_offset = 0


def get_particles(data_frame, color_dictionary: dict):
    global color_offset
    particles = Particles()
    particles.create_property('Particle Identifier', data=data_frame.id)
    particles.create_property("Position", data=np.array((data_frame.x, data_frame.y, data_frame.z)).T)
    particles.create_property('Radius', data=data_frame.radius)
    particles.create_property('Color', data=np.array(color_dictionary[color_offset]))
    color_offset += 1
    return particles


if __name__ == '__main__':
    export_to_ovito(sys.argv[1])
