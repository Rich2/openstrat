package ostrat
package geom

/** This just a temporary start. */
object Cross
{
  /** Temporary start. */
 def apply(scale: Double = 1) =
 { val lh = Line2(-10 vv 0, 10 vv 0)
   val rh =  Line2(0 vv 10, 0 vv -10)
   Line2s(lh, rh).scale(scale).draw(2)
 }
}
