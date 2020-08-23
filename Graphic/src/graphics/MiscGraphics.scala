package ostrat
package geom

object Star5
{
  val classicRatio = 0.382

  def apply(ratio: Double = classicRatio): Polygon =
  { val l2: Vec2s = Vec2s(Vec2(0, 1), Vec2(0, ratio).rotate(-deg36))
    iToFlatMap(0, 4)(i => l2.rotate(-deg72 * i)).toPolygon
  }

  def fill(colour: Colour, ratio: Double = classicRatio): PolygonFill = apply(ratio).fillOld(colour)
  
  def draw(lineWidth: Double = 1, colour: Colour): PolygonDraw = apply().drawOld(lineWidth, colour)

  def crossLines(ratio: Double = classicRatio) =
  {
    val poly = apply(ratio)
    Ints(0, 4, 8, 2, 6, 0).map{i => poly(i)}.toPathDraw(1)
  }

  /** Not quite sure what this does. */
  def ptUpYCentred(ratio: Double = classicRatio): Polygon = apply(ratio).slateY(deg36.cos / 2 - 0.5)
}

object Star7
{
  val classicRatio = 0.692

  def apply(ratio: Double = classicRatio): Polygon =
  { val l2: Vec2s = Vec2s(Vec2(0, 1), Vec2(0, ratio).rotate(-Angle(180 / 7)))
    iToFlatMap(0, 6)(i => l2.rotate(Angle(-360 / 7) * i)).toPolygon
  }
  
  def fill(colour: Colour, ratio: Double = classicRatio): PolygonFill = apply(ratio).fillOld(colour)
}

object Star3
{
  def apply(sideLength: Double = 1): Polygon =
  { Polygon((0 vv sideLength * math.sqrt(3) / 3),
              (sideLength / 2 vv -sideLength * math.sqrt(3) / 6),
              (-sideLength / 2 vv -sideLength * math.sqrt(3) / 6))
  }
    
  def draw(lineWidth: Double = 1, colour: Colour): PolygonDraw = apply().drawOld(lineWidth, colour)

  def fill(colour: Colour): PolygonFill = apply().fillOld(colour)

}

object Pentagram
{
  def apply(): Polygon =
  { val l2: Vec2s = Vec2s(Vec2(0, 1), Vec2(0, -1).rotate(-deg36))
    iToFlatMap(0, 4)(i => l2.rotate(-deg72 * i)).toPolygon
  }

  def draw(lineWidth: Double = 1, colour: Colour): PolygonDraw = apply().drawOld(lineWidth, colour)

}
