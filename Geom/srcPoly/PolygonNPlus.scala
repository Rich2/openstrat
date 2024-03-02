/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A [[Polygon]] with at least 3 vertices. The PolygonNPlus traits include values for the vertices and the x and y components of the vertices. The X
 * and Y components are included because Graphics implementation APIs use them. */
trait Polygon3Plus extends Any with Polygon
{ type ThisT <: Polygon3Plus

  /** The X component of the v1 vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 0 vertex at or
      immediately clockwise from 12 o'clock. */
  final def v1x: Double = arrayUnsafe(2)

  /** The Y component of the v1 vertex. The default convention is for the vertices to be numbered in a clockwise direction with vertex immediately
   *  clockwise from 12 o'clock. */
  final def v1y: Double = arrayUnsafe(3)

  /** The v1 Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1 immediately clockwise from 12
   *  o'clock. */
  final def v1: Pt2 = v1x pp v1y

  /** The X component of the v2 vertex. The default convention is for the vertices to be numbered in a clockwise direction with vertex 1 immediately
   *  clockwise from 12 o'clock. */
  final def v2x: Double = arrayUnsafe(4)

  /** The Y component of the v2 vertex. The default convention is for the vertices to be numbered in a clockwise direction with vertex immediately
   *  clockwise from 12 o'clock. */
  final def v2y: Double = arrayUnsafe(5)

  /** The v2 vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 0 vertex at or immediately clockwise
   * from 12 o'clock. */
  final def v2: Pt2 = v2x pp v2y

  /** Polygon side 1 from vertex 0 to vertex 1. */
  final def side1: LineSeg = LineSeg(v1, v2)

  /** Polygon side 2 from vertex 1 to vertex 2. */
  final def side2: LineSeg = LineSeg(v2, vert(3))

  /** The X component of the centre or half way point of side 1 of this polygon. The midway point between v0 and v1. */
  final def sd1CenX: Double = v0x \/ v1x

  /** The Y component of the centre or half way point of side 1 of this polygon. the midway point between v1 and v2. */
  final def sd1CenY: Double = v0y \/ v1y

  /** The centre or half way point of side 1 of this polygon. Side 1 starts at the v1 vertex and ends at the v2 vertex. This can be thought of as
   * vertex 1.5 */
  final def sd1Cen: Pt2 = sd1CenX pp sd1CenY

  /** The X component of the centre or half way point of side 2 of this polygon. */
  final def sd2CenX: Double = v1x \/ v2x

  /** The Y component of the centre or half way point of side 2 of this polygon. */
  final def sd2CenY: Double = v1y \/ v2y

  /** The centre or half way point of side 2 of this polygon. Side 2 starts at the v1 vertex and ends at the v2 vertex. This can be thought of as
   *  vertex 2.5. */
  final def sd2Cen: Pt2 = sd2CenX pp sd2CenY
}

/** A [[Polygon]] with at least 4 vertices. */
trait Polygon4Plus extends Any with Polygon3Plus with SeqSpecDbl2[Pt2]
{ type ThisT <: Polygon4Plus

  /** The X component of vertex 3. The default convention is for the vertices to be numbered in a clockwise direction with vertex 1 immediately
      clockwise from 12 o'clock. */
  final def v3x: Double = arrayUnsafe(6)

  /** The Y component of the 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with vertex 1 immediately
   *  clockwise from 12 o'clock. */
  final def v3y: Double = arrayUnsafe(7)

  /** Vertex 4. The default convention is for the vertices to be numbered in a clockwise direction with the vertex 1 immediately clockwise from 12
   *  o'clock. */
  final def v3: Pt2 = v3x pp v3y

  /** The X component of the centre or half way point of side 3 of this polygon. */
  final def sd3CenX: Double = v2x \/ v3x

  /** The Y component of the centre or half way point of side 3 of this polygon. */
  final def sd3CenY: Double = v2y \/ v3y

  /** The centre or half way point of side 3 of this polygon. */
  final def sd3Cen: Pt2 = sd3CenX pp sd3CenY
}

/** A [[Polygon]] with at least 5 vertices. */
trait Polygon5Plus extends Any with Polygon4Plus with SeqSpecDbl2[Pt2]
{ type ThisT <: Polygon5Plus

  /** The X component of the vertex 4. */
  final def v4x: Double = arrayUnsafe(8)

  /** The Y component of the vertex 4. */
  final def v4y: Double = arrayUnsafe(9)

  /** Vertex 4. The default convention is for the vertices to be numbered in a clockwise direction with the vertex 1 immediately clockwise from 12
   *  o'clock. */
  inline final def v4: Pt2 = v4x pp v4y

  /** The X component of the centre or half way point of side 5 of this polygon. Side 5 starts at the v4 vertex and ends at the v5 vertex. This can be
   *  thought of as vertex 4.5. */
  final def sd4CenX: Double = v3x \/ v4x

  /** The Y component of the centre or half way point of side 5 of this polygon. Side 5 starts at the v4 vertex and ends at the v5 vertex. This can be
   *  thought of as vertex 4.5. */
  final def sd4CenY: Double = v3y \/ v4y

  /** The centre or half way point of side 5 of this polygon. Side 5 starts at the v4 vertex and ends at the v5 vertex. This can be thought of as
   * vertex 4.5. */
  inline final def sd4Cen: Pt2 = v3x pp v3y
}

/** A [[Polygon]] with at least 6 vertices. */
trait Polygon6Plus extends Any with Polygon5Plus
{ type ThisT <: Polygon6Plus

  /** The X component of the 6th Vertex. */
  final def v5x: Double = arrayUnsafe(10)

  /** The Y component of the 6th Vertex. */
  final def v5y: Double = arrayUnsafe(11)

  /** Vertex 5. The default convention is for the vertices to be numbered in a clockwise direction with the vertex 1 immediately clockwise from 12
   *  o'clock. */
  final def v5: Pt2 = v5x pp v5y

  /** The X component of the centre or half way point of side 6 of this polygon. */
  final def sd5CenX: Double = v4x \/ v5x

  /** The Y component of the centre or half way point of side 5 of this polygon. */
  final def sd5CenY: Double = v4y \/ v5y

  /** The centre or half way point of side 5 of this polygon. Side 5 starts at the v5 vertex and ends at the v6 vertex. This can be thought of as
   * vertex 5.5. */
  inline final def sd5Cen: Pt2 = sd5CenX pp sd5CenY
}