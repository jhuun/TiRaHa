package labyrinthsolver;

/**
 * "Oikean käden sääntö" -algoritmi labyrintin ratkaisemiseen.
 *
 * @author Juri Kuronen
 */
public class WallFollower extends LabyrinthSolver {

    /**
     * Yliluokka alustaa random-olion.
     *
     * @see labyrinthsolver.LabyrinthSolver#LabyrinthSolver()
     */
    public WallFollower() {
        super();
        name = "Wall follower";
    }

    /**
     * <u>Alustus:</u><br>
     * Alustaa visited-arrayn.
     * <br><br>
     * <u>Toiminta:</u><br>
     * Noudattaa ratkaisemisessa "oikean käden sääntöä".
     *
     * @return Palauttaa true, jos labyrintti ratkaistiin.
     * @see main.Labyrinth#getTargetCoordinate(int, byte)
     */
    @Override
    public boolean solveLabyrinth() {
        int width = labyrinth.getWidth();
        int height = labyrinth.getHeight();
        visited = new int[height][width];
        int coordinate = 0;
        visited[0][0] = 2;
        int targetCoordinate = width * height - 1;
        /*
         Alustavasti muuri länteen ja kävelysuunta etelään.
         */
        byte wallDirection = 8, walkDirection = 4;
        while (coordinate != targetCoordinate) {
            /*
             Jos asetettuun muurin suuntaan voi alkaa kävellä, vaihdetaan kävelysuunta sinne.
             */
            if (labyrinth.hasEdge(coordinate, wallDirection)) {
                walkDirection = wallDirection;
                wallDirection = (byte) ((wallDirection * 2) % 15);
            }
            /*
             Jos kävelysuunnassa on muuri, etsitään uusi kävelysuunta.
             */
            while (!labyrinth.hasEdge(coordinate, walkDirection)) {
                wallDirection = walkDirection;
                if (walkDirection == 1) {
                    walkDirection = 8;
                } else {
                    walkDirection /= 2;
                }
            }
            /*
             Päivitetään koordinaatti, missä mennään.
             */
            try {
                coordinate = labyrinth.getTargetCoordinate(coordinate, walkDirection);
            } catch (Exception ex) {
                System.out.println("For some reason the target coordinate was not in labyrinth.");
            }
            visited[coordinate / width][coordinate % width] = 2;
        }
        return true;
    }

    /**
     * Kts. kuvaus solveLabyrinth()-metodista. Tämän metodin ainoa ero on, ettei
     * se ylläpidä visited-arraytä.
     *
     * @return Palauttaa true, jos labyrintti ratkaistiin.
     * @see labyrinthsolver.WallFollower#solveLabyrinth()
     */
    public boolean minorlySpedUpSolver() {
        int width = labyrinth.getWidth();
        int height = labyrinth.getHeight();
        int coordinate = 0;
        int targetCoordinate = width * height - 1;
        /*
         Alustavasti muuri länteen ja kävelysuunta etelään.
         */
        byte wallDirection = 8, walkDirection = 4;
        while (coordinate != targetCoordinate) {
            /*
             Jos asetettuun muurin suuntaan voi alkaa kävellä, vaihdetaan kävelysuunta sinne.
             */
            if (labyrinth.hasEdge(coordinate, wallDirection)) {
                walkDirection = wallDirection;
                wallDirection = (byte) ((wallDirection * 2) % 15);
            }
            /*
             Jos kävelysuunnassa on muuri, etsitään uusi kävelysuunta.
             */
            while (!labyrinth.hasEdge(coordinate, walkDirection)) {
                wallDirection = walkDirection;
                if (walkDirection == 1) {
                    walkDirection = 8;
                } else {
                    walkDirection /= 2;
                }
            }
            /*
             Päivitetään koordinaatti, missä mennään.
             */
            try {
                coordinate = labyrinth.getTargetCoordinate(coordinate, walkDirection);
            } catch (Exception ex) {
                System.out.println("For some reason the target coordinate was not in labyrinth.");
            }
        }
        return true;
    }
}
