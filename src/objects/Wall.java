import src.objects.GameObject;

/**
 * Walls don't need anything fancy, so all we need to do is just pick a
 * char representation. If we want to change it in the future, do that here.
 */
public class Wall extends GameObject {

    public Wall() {
//        super('□'); // Any UTF-16 character should work
        super('#');
    }
}
