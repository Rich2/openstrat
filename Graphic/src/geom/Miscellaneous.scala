package ostrat
package geom

object Star5
{
  /** The ratio for the standard star is approx 0.4. */
  def apply(ratio: Double = 0.4): Polygon =
  { val l2: Vec2s = Vec2s(Vec2(0, 1), Vec2(0, ratio).rotate(-deg36))
    iToFlatMap(0, 4)(i => l2.rotate(-deg72 * i)).toPolygon
  }

  /** Not quite sure what this does. */
  def ptUpYCentred(ratio: Double = 0.5): Polygon = apply(ratio).slateY(deg36.cos / 2 - 0.5)
}