package ostrat
package geom

/** This is the base trait for TransExtension[T] and TransDistExtension. An object that can transform itself in a 2d geometry. This is a key trait, the object can be
 *  transformed in 2 dimensional space. Leaf classes must implement the single method fTrans(f: VT => VT):  T. */
trait TransAllGenExtension[T] extends Any
{ 

}