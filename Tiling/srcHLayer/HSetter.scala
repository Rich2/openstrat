/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Helper trait for setting an [[LayerHcRefSys]], [[HSepLayer]] and a [[HCornerLayer]] at the same time. This allows the basic geometry of the terrain to be
 *  laid out in systematic row order. There will be tile rows and vertex rows. It is assumed that you will want to specify the values for nearly every tile
 *  [[HCen]]. Therefore, the column of the tile is determined by its position in the row sequence. It is assumed that the majority of the [[HSep]]s, at least
 *  initially will have default empty / none values. Hence, the setters for the vertex rows must specify there column. */
trait HSetter[TT <: AnyRef, ST, SST <: ST & HSepSome]
{ implicit def grid: HGrid

  /** The [[LayerHcRefSys]]. The [[HCen]] tile values. */
  def terrs: LayerHcRefSys[TT]

  /** The [[LayerHSOptSys]]. The [[HSep]] separator values. */
  def sTerrs: LayerHSOptSys[ST, SST]

  /** The [[HCorner]] layer to set the vertices of the [[HSep]]s. */
  def corners: HCornerLayer

  /** A tile row element. Your tile rows will include these in addition to straight tile values. */
  trait TRowElemBase
  { /** Sets [[HCen]] and [[HSep]] data and HCorner values. */
    def run(row: Int, c: Int): Unit
  }

  trait IsleNBase extends TRowElemBase
  {
    /** The tile terrain. typically land terrain. */
    def terr: TT

    /** The [[HSep]] separator terrain for sep0. Typically, water terrain, */
    def sepTerr0: SST

    /** The [[HSep]] separator terrain for sep0. Typically, water terrain, */
    def sepTerr1: SST

    /** The [[HSep]] separator terrain for sep0. Typically, water terrain, */
    def sepTerr2: SST

    /** The [[HSep]] separator terrain for sep0. Typically, water terrain, */
    def sepTerr3: SST

    /** The [[HSep]] separator terrain for sep0. Typically, water terrain, */
    def sepTerr4: SST

    /** The [[HSep]] separator terrain for sep0. Typically, water terrain, */
    def sepTerr5: SST

    def magnitude: Int

    def setTerrs(row: Int, c: Int): Unit =
    { terrs.set(row, c, terr)
      val s0 = HSep(row + 1, c + 1)
      sTerrs.setExists(grid, s0, sepTerr0)
      val s1 = HSep(row, c + 2)
      sTerrs.setExists(grid, s1, sepTerr1)
      val s2 = HSep(row - 1, c + 1)
      sTerrs.setExists(grid, s2, sepTerr2)
      val s3 = HSep(row - 1, c - 1)
      sTerrs.setExists(grid, s3, sepTerr3)
      val s4 = HSep(row, c - 2)      
      sTerrs.setExists(grid, s4, sepTerr4)
      val s5 = HSep(row + 1, c - 1)
      sTerrs.setExists(grid, s5, sepTerr5)
    }
  }

  /** Terrain setter trait for (geometrical) island terrain. where all the [[HSep]] separators have the same terrain type.  */
  trait IsleNBaseHomo extends IsleNBase
  { /** The [[HSep]] separator terrain for all 6 [[HSep]]s. Typically, water terrain or a wall on decimetre scale maps. */
    def sepTerrs: SST

    /** The [[HSep]] separator terrain for [[HSep]]0 the island's upper right separator. Typically, water terrain or a wall on decimetre scale maps. */
    override def sepTerr0: SST = sepTerrs

    /** The [[HSep]] separator terrain for [[HSep]]1 the island's right separator. Typically, water terrain or a wall on decimetre scale maps. */
    override def sepTerr1: SST = sepTerrs

    /** The [[HSep]] separator terrain for [[HSep]]2 the island's lower right separator. Typically, water terrain or a wall on decimetre scale maps. */
    override def sepTerr2: SST = sepTerrs

    /** The [[HSep]] separator terrain for [[HSep]]3 the island's lower left separator. Typically, water terrain or a wall on decimetre scale maps. */
    override def sepTerr3: SST = sepTerrs

    /** The [[HSep]] separator terrain for [[HSep]]4 the island's left separator. Typically, water terrain or a wall on decimetre scale maps. */
    override def sepTerr4: SST = sepTerrs

    /** The [[HSep]] separator terrain for [[HSep]]5 the island's upper left separator. Typically, water terrain or a wall on decimetre scale maps. */
    override def sepTerr5: SST = sepTerrs
  }

  trait IsleNLargeBase extends IsleNBase
  {
    override def run(row: Int, c: Int): Unit =
    { setTerrs(row, c)
      corners.setBend0(row, c, magnitude, 6 - magnitude)
      corners.setBend1(row, c, magnitude, 6 - magnitude)
      corners.setBend2(row, c, magnitude, 6 - magnitude)
      corners.setBend3(row, c, magnitude, 6 - magnitude)
      corners.setBend4(row, c, magnitude, 6 - magnitude)
      corners.setBend5(row, c, magnitude, 6 - magnitude)
    }
  }

  /** Sets the [[HSep]] separators terrain and [[HCorner]]s for an Island or geometrically analogous terrain, with a radius set in the sub traits. Only use
   * these classes for hexs where there is no offset for any of the adjacent hex's [[HCorner]]s.  */
  trait IsleNSmallBase extends IsleNBase
  {
    override def run(row: Int, c: Int): Unit =
    { setTerrs(row, c)
      corners.setNCornersIn(row, c, 6, 0, magnitude)
    }
  }

  /** Sets the [[HCen]] terrain, the  [[HSep]] terrains and the [[HCorner]]s for an Island, with a radius of 13/16 of the radius of the hex. Only use these
   *  classes for hexs where there is no offset for any of the adjacent hex's [[HCorner]]s on shared [[HVert]]s. */
  trait Isle13Base extends IsleNLargeBase
  { override def magnitude: Int = 3
  }

  /** Sets the [[HCen]] terrain, the  [[HSep]] terrains and the [[HCorner]]s for an Island, with a radius of 12/16 of the radius of the hex. Only use these
   *  classes for hexs where there is no offset for any of the adjacent hex's [[HCorner]]s on shared [[HVert]]s. */
  trait Isle12Base extends IsleNLargeBase
  { override def magnitude: Int = 4
  }

  /** Sets the [[HCen]] terrain, the  [[HSep]] terrains and the [[HCorner]]s for an Island, with a radius of 11/16 of the radius of the hex. Only use these
   *  classes for hexs where there is no offset for any of the adjacent hex's [[HCorner]]s on shared [[HVert]]s. */
  trait Isle11Base extends IsleNLargeBase
  { override def magnitude: Int = 5
  }

  /** Sets the [[HCen]] terrain, the  [[HSep]] terrains and the [[HCorner]]s for an Island, with a radius of 10/16 of the radius of the hex. Only use these
   *  classes for hexs where there is no offset for any of the adjacent hex's [[HCorner]]s on shared [[HVert]]s. */
  trait Isle10Base extends IsleNSmallBase
  { override def magnitude: Int = 6
  }

  /** Sets the [[HCen]] terrain, the  [[HSep]] terrains and the [[HCorner]]s for an Island, with a radius of 9/16 of the radius of the hex. Only use these
   *  classes for hexs where there is no offset for any of the adjacent hex's [[HCorner]]s on shared [[HVert]]s. */
  trait Isle9Base extends IsleNSmallBase
  { override def magnitude: Int = 7
  }

  /** Sets the [[HCen]] terrain, the [[HSep]] terrains and the [[HCorner]]s for an Island, with a radius of 8/16 of the radius of the hex. Only use these
   *  classes for hexs where there is no offset for any of the adjacent hex's [[HCorner]]s on shared [[HVert]]s. */
  trait Isle8Base extends IsleNSmallBase
  { override def magnitude: Int = 8
  }

  /** Sets the [[HCen]] terrain, the [[HSep]] terrains and the [[HCorner]]s for an Island, with a radius of 7/16 of the radius of the hex. Only use these
   *  classes for hexs where there is no offset for any of the adjacent hex's [[HCorner]]s on shared [[HVert]]s. */
  trait Isle7Base extends IsleNSmallBase
  { override def magnitude: Int = 9
  }

  /** Sets the [[HCen]] terrain, the [[HSep]] terrains and the [[HCorner]]s for an Island, with a radius of 6/16 of the radius of the hex. Only use* these
   *  classes for hexs where there is no offset for any of the adjacent hex's [[HCorner]]s on shared [[HVert]]s. */
  trait Isle6Base extends IsleNSmallBase
  { override def magnitude: Int = 10
  }

  /** Sets the [[HCen]] terrain, the [[HSep]] terrains and the [[HCorner]]s for an Island, with a radius of 5/16 of the radius of the hex. Only use these
   *  classes for hexs where there is no offset for any of the adjacent hex's [[HCorner]]s on shared [[HVert]]s. */
  trait Isle5Base extends IsleNSmallBase
  { override def magnitude: Int = 11
  }

  /** Sets the [[HCen]] terrain, the [[HSep]] terrains and the [[HCorner]]s for an Island, with a radius of 4/16 of the radius of the hex. Only use these
   * classes for hexs where there is no offset for any of the adjacent hex's [[HCorner]]s on shared [[HVert]]s. */
  trait Isle4Base extends IsleNSmallBase
  { override def magnitude: Int = 12
  }

  /** Sets the [[HCen]] terrain, the [[HSep]] terrains and the [[HCorner]]s for an Island, with a radius of 3/16 of the radius of the hex. Only use these
   *  classes for hexs where there is no offset for any of the adjacent hex's [[HCorner]]s on shared [[HVert]]s. */
  trait Isle3Base extends IsleNSmallBase
  { override def magnitude: Int = 13
  }

  /** Sets an [[HSepB]] separator in the tile row. Make sure you do not add 4 to the column coordinate after applying this. */
  trait SepBBase extends TRowElemBase
  { /** The [[HSep]] separator terrain. */
    def sTerr: SST

    override def run(row: Int, c: Int): Unit = sTerrs.setExists(grid, row, c - 2, sTerr)
  }

  /** Base trait for capes / headlands / peninsulas. Only use these classes for [[HVert]]s where there is no offset for any of the adjacent hex's [[HCorner]]s
   *  on shared [[HVert]]s. */
  trait CapeBase
  { /** The number of the first vertex to be indented. */
    def indentStartIndex: Int

    /** The number of indented vertices. */
    def numIndentedVerts: Int

    /** The magnitude of the [[HCorner]] indents. */
    def magnitude: Int

    /** The terrain of the main tile, typically a type of land. */
    def terr: TT

    /** The terrain of the [[HSep]] separators, typically a type of water. */
    def sepTerrs: SST

    def run(row: Int, c: Int): Unit =
    { terrs.set(row, c, terr)
      corners.setNCornersIn(row, c, numIndentedVerts, indentStartIndex, magnitude)

      iUntilForeach(-1, numIndentedVerts) { i0 =>
        val i: Int = (indentStartIndex + i0) %% 6
        val sep: HSep = HCen(row, c).sep(i)
        sTerrs.setExists(grid, sep, sepTerrs)
      }
    }
  }

  /** Needs removing. Base trait [Isthmus](https://en.wikipedia.org/wiki/Isthmus). Generally this will be used for Isthmuses, but it can be used for any
   *  [[HCen]] and [[HSep]] terrain that fits the geometry. */
  trait IsthmusBase
  { /** The number of the first vertex to be indented. */
    def indentIndex: Int

    /** The number of the first vertex to be indented. */
    def oppositeIndex: Int = (indentIndex + 3) %% 6

    /** The terrain of the main tile, typically a type of land. */
    def terr: TT

    /** The terrain of the [[HSep]]s, next to the index vertex, typically a type of water. */
    def sepTerrs1: SST

    /** The terrain of the [[HSep]]s, next to the opposite vertex typically a type of water. */
    def sepTerrs2: SST

    /** Sets the [[HCen]] terrain. Sets the two opposite [[HCorner]]s and sets the four [[HSep]] terrains adjacent to the pulled in vertices. The [[HSep]]
     * terrains can be different on either [[HSep]] of the isthmus. For eample it could be sea on one [[HSep]] and fresh water lake on the other. */
    def run(row: Int, c: Int): Unit =
    { terrs.set(row, c, terr)
      corners.setCornerIn(row, c, indentIndex, 7)
      corners.setCornerIn(row, c, oppositeIndex, 7)

      sTerrs.setExists(grid, HCen(row, c).sep(indentIndex - 1), sepTerrs1)
      sTerrs.setExists(grid, HCen(row, c).sep(indentIndex), sepTerrs1)
      sTerrs.setExists(grid, HCen(row, c).sep(indentIndex - 4), sepTerrs2)
      sTerrs.setExists(grid, HCen(row, c).sep(indentIndex + 3), sepTerrs2)
    }
  }

  trait VertSetBase
  { /** The C coordinate of the vertex. */
    def c: Int
  }

  trait OrigBase extends VertSetBase
  {
    /** The terrain of the [[HSep]] from this end point oe source. */
    def sTerr: SST

    /** The direction from the [[HVert]] along the [[HSep]]. */
    def dirn: HVDirnPrimary

    def setOrigSep(row: Int): Unit = dirn match
    { case HVUp => sTerrs.setExists(grid, row + 1, c, sTerr)
      case HVUR => sTerrs.setExists(grid, row, c + 1, sTerr)
      case HVDR => sTerrs.setExists(grid, row, c + 1, sTerr)
      case HVDn => sTerrs.setExists(grid, row - 1, c, sTerr)
      case HVDL => sTerrs.setExists(grid, row, c - 1, sTerr)
      case HVUL => sTerrs.setExists(grid, row, c - 1, sTerr)
    }
  }
  
  /** Sets origin / end point of an [[HSep]] hex tile separator. The direction is given by the view from the [[HVert]] deon the [[HSep]]. */
  trait OrigLtRtBase extends OrigBase
  {
    /** The magnitude of the offset to the left of the [[HVert]] as viewed from the source. */
    def magLt: Int

    /** The magnitude of the offset to the right of the [[HVert]] as viewed from the source. */
    def magRt: Int

    def run(row: Int): Unit =
    { corners.setVertOrig(row, c, dirn, magLt, magRt)
      setOrigSep(row)
    }
  }

  /** Sets origin / end point of an [[HSep]] hex tile separator. The direction is given by the view from the [[HVert]] deon the [[HSep]]. This is offset to the
   *  left from the same view. */
  trait OrigLtBase extends OrigBase
  { /** The magnitude of the offset to the left of the [[HVert]] as viewed from the source. */
    def magLt: Int
    def run(row: Int): Unit =
    { corners.setOrigLt(row, c, dirn, magLt)
      setOrigSep(row)
    }
  }

  /** Sets origin / end point of an [[HSep]] hex tile separator. The direction is given by the view from the [[HVert]] deon the [[HSep]]. This is offset to the
   *  right from the same view. */
  trait OrigRtBase extends OrigBase
  { /** The magnitude of the offset to the right of the [[HVert]] as viewed from the source. */
    def magRt: Int

    def run(row: Int): Unit =
    { corners.setOrigRt(row, c, dirn, magRt)
      setOrigSep(row)
    }
  }

  /** Sets only the inside [[HCorner]] of Vertex for a bend [[HSep]] terrain, Sets the left most of the [[HSep]]s of this vertex. The orientation of the bend is
   * specified by the direction of the inside of the bend. This trait is provided to model real world geographic / terrain features and is probably superfluous
   * for created worlds / terrain. */
  trait BendBase extends VertSetBase
  { /** The direction of the [[HCen]] at the inside of the bend from the HVert. */
    def dirn: HVDirn

    /** The terrain of the left [[HSep]] of the junction as seen from the inside of the bend. */
    def leftTerr: SST

    /** The terrain of the right [[HSep]] of the junction as seen from the inside of the bend. */
    def rightTerr: SST

    final def run(row: Int): Unit =
    { setCorners(row)
      setSeparators(row)
    }

    def setCorners(row: Int): Unit

    def setSeparators(row: Int): Unit = dirn match
    { case HVUR =>
      { sTerrs.setExists(grid, row, c + 1, leftTerr)
        sTerrs.setExists(grid, row + 1, c, rightTerr)
      }

      case HVDR =>
      { sTerrs.setExists(grid, row - 1, c, leftTerr)
        sTerrs.setExists(grid, row, c + 1, rightTerr)
      }

      case HVDn =>
      { sTerrs.setExists(grid, row, c - 1, leftTerr)
        sTerrs.setExists(grid, row, c + 1, rightTerr)
      }

      case HVDL =>
      { sTerrs.setExists(grid, row, c - 1, leftTerr)
        sTerrs.setExists(grid, row - 1, c, rightTerr)
      }

      case HVUL =>
      { sTerrs.setExists(grid, row + 1, c, leftTerr)
        sTerrs.setExists(grid, row, c - 1, rightTerr)

      }

      case HVUp =>
      { sTerrs.setExists(grid, row, c + 1, leftTerr)
        sTerrs.setExists(grid, row, c - 1, rightTerr)
      }

      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  /** Sets all the corners of Vertex for a bend [[HSep]] terrain, Sets the left most of the [[HSep]]s of this vertex. The orientation of the bend is specified
   * by the direction of the inside of the bend. This trait is provided to model real world geographic / terrain features and is probably superfluous for
   * created worlds / terrain. */
  trait BendInOutBase extends BendBase
  { /** The magnitude of the offset for inside of the bend. */
    def magIn: Int

    /** The magnitude of the offset for the outside of bend. */
    def magOut: Int

    override def setCorners(row: Int): Unit = dirn match
    { case HVUR => corners.setBend4(row + 1, c + 2, magIn, magOut)
      case HVDR => corners.setBend5(row - 1, c + 2, magIn, magOut)
      case HVDn => corners.setBend0(row - 1, c, magIn, magOut)
      case HVDL => corners.setBend1(row - 1, c - 2, magIn, magOut)
      case HVUL => corners.setBend2(row + 1, c - 2, magIn, magOut)
      case HVUp => corners.setBend3(row + 1, c, magIn, magOut)
      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  /** So I think this is only useful on edge of a grid where it meets a second grid. Sets all the corners of Vertex for a bend [[HSep]] terrain, with extra
   * separator vertex, Sets the left most of the [[HSep]]s of this vertex. The orientation of the bend is specified by the direction of the inside of the bend.
   * This trait is provided to model real world geographic / terrain features and is probably superfluous for created worlds / terrain. */
  trait BendInOutExtraBase extends BendInOutBase
  {
    override def setCorners(row: Int): Unit = dirn match
    { case HVUR => corners.setBendExtra4(row + 1, c + 2, magIn, magOut)
      case HVDR => corners.setBendExtra5(row - 1, c + 2, magIn, magOut)
      case HVDn => corners.setBendExtra0(row - 1, c, magIn, magOut)
      case HVDL => corners.setBendExtra1(row - 1, c - 2, magIn, magOut)
      case HVUL => corners.setBendExtra2(row + 1, c - 2, magIn, magOut)
      case HVUp => corners.setBendExtra3(row + 1, c, magIn, magOut)
      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  /** Sets the 2 outer corners of the bend for [[HSep]] terrain, Also sets the left most of the [[HSep]]s of the bend vertex. The orientation of the bend is
   * specified by the direction of the inside of the bend. */
  trait BendOutBase extends BendBase
  { /** The magnitude of the offset for the 2 outer corners of the bend vertex. */
    def magnitude: Int

    override def setCorners(row: Int): Unit = dirn match
    { case HVUR => corners.setBend4Out(row + 1, c + 2, magnitude)
      case HVDR => corners.setBend5Out(row - 1, c + 2, magnitude)
      case HVDn => corners.setVert0Out(row - 1, c, magnitude)
      case HVDL => corners.setBend1Out(row - 1, c - 2, magnitude)
      case HVUL => corners.setBend2Out(row + 1, c - 2, magnitude)
      case HVUp => corners.setBend3Out(row + 1, c, magnitude)
      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  /** Sets only the inside [[HCorner]] of Vertex for a bend in [[HSep]]s terrain, Sets the left most of the [[HSep]]s of this vertex. The orientation
   *  of the bend is specified by the direction of the inside of the bend. This trait is provided to model real world geographic / terrain features
   *  and is probably superfluous for created worlds / terrain. */
  trait BendInBase extends BendBase
  { /** The magnitude of the offset on the inside [[HCorner]]. */
    def magnitude: Int

    override def setCorners(row: Int): Unit = dirn match
    { case HVUR => corners.setCornerIn(row + 1, c + 2, 4, magnitude)
      case HVDR => corners.setCornerIn(row - 1, c + 2, 5, magnitude)
      case HVDn => corners.setCornerIn(row - 1, c, 0, magnitude)
      case HVDL => corners.setCornerIn(row - 1, c - 2, 1, magnitude)
      case HVUL => corners.setCornerIn(row + 1, c - 2, 2, magnitude)
      case HVUp => corners.setCornerIn(row + 1, c, 3, magnitude)
      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  /** Sets only the inside [[HCorner]] of Vertex for a bend in [[HSep]]s terrain, Sets the left most of the [[HSep]]s of this vertex. The orientation of the
   * bend is specified by the direction of the inside of the bend. This trait is provided to model real world geographic / terrain features and is probably
   * superfluous for created worlds / terrain. */
  trait BendInLtBase extends BendBase
  { /** The magnitude of the offset on the inside [[HCorner]]. */
    def magIn: Int

    /** The magnitude of the offset on the origin [[HCorner]]. */
    def OrigMag: Int

    override def setCorners(row: Int): Unit = dirn match
    { case HVUR =>
      { corners.setCornerIn(row + 1, c + 2, 4, magIn)
        corners.setCorner(row - 1, c, 0, HVDL, OrigMag)
        corners.setCornerPair(row + 1, c - 2, 2, HVExact, 0, HVDL, OrigMag)
      }

      case HVDR =>
      { corners.setCornerIn(row - 1, c + 2, 5, magIn)
        corners.setCornerSepExtra(row - 1, c - 2, 1, HVUL, OrigMag)
        corners.setCornerPair(row + 1, c, 3, HVUR, 0, HVUL, OrigMag)
      }

      case HVDn =>
      { corners.setCornerIn(row - 1, c, 0, magIn)
        corners.setCornerPair(row + 1, c + 2, 4, HVExact, 0, HVUp, OrigMag)
        corners.setCornerSepExtra(row + 1, c - 2, 2, HVUp, OrigMag)
      }

      case HVDL =>
      { corners.setCornerIn(row - 1, c - 2, 1, magIn)
        corners.setCorner(row + 1, c, 3, HVUL, OrigMag)
        corners.setCorner(row - 1, c + 2, 5, HVUL, OrigMag)
      }

      case HVUL =>
      { corners.setCornerIn(row + 1, c - 2, 2, magIn)
        corners.setCorner(row + 1, c + 2, 4, HVDR, OrigMag)
        corners.setCornerPair(row - 1, c, 0, HVDR, OrigMag, HVExact, 0)
      }

      case HVUp =>
      { corners.setCornerSepExtra(row + 1, c, 3, HVUp, magIn)
        corners.setCornerSepExtra(row - 1, c + 2, 5, HVDn, OrigMag)
        corners.setCornerPair(row - 1, c - 2, 1, HVExact, 0, HVDn, OrigMag)
      }
      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  trait BendInRtBase extends BendBase
  { /** The magnitude of the offset on the inside [[HCorner]]. */
    def magIn: Int

    /** The magnitude of the offset on the origin [[HCorner]]. */
    def origMag: Int

    override def setCorners(row: Int): Unit = dirn match
    { case HVUR =>
      { corners.setCornerIn(row + 1, c + 2, 4, magIn)
        debexc("Not Implemented")
      }

      case HVDR =>
      { corners.setCornerIn(row - 1, c + 2, 5, magIn)
        corners.setCorner(row + 1, c, 3, HVUL, origMag)
        corners.setCornerPair(row - 1, c - 2, 1, HVUL, origMag, HVExact, 0)
      }

      case HVDn =>
      { corners.setCornerIn(row - 1, c, 0, magIn)
        corners.setCorner(row + 1, c + 2, 4, HVUp, origMag)
      }

      case HVDL =>
      { corners.setCornerIn(row - 1, c - 2, 1, magIn)
        corners.setCorner(row - 1, c + 2, 5, HVUR, origMag)
        corners.setCorner(row + 1, c, 3, HVUR, origMag)
      }

      case HVUL =>
      { corners.setCornerIn(row + 1, c - 2, 2, magIn)
        corners.setCorner(row - 1, c, 0, HVDR, origMag)
      }

      case HVUp => //This seems to be correctly implemented
      { corners.setCornerSepExtra(row + 1, c, 3, HVUp, magIn)
        corners.setCornerSepExtra(row - 1, c - 2, 1, HVDn, origMag)
        //corners.setCornerPair(row - 1, c + 2, 5, HVDn, origMag, HVExact, 0)
      }
      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  /** Used for setting a vertex where 3 [[HSep]] terrains meet. Also sets the left most [[HSep]]. This trait is provided to model real world
   *  geographic / terrain features and is probably superfluous for created worlds / terrain. */
  trait ThreeUpBase extends VertSetBase
  { /** Separator terrain for the [[HSep]] that is up from the [[HVert]]. */
    def upTerr: SST

    /** Separator terrain for the [[HSep]] that is down-right from the [[HVert]]. */
    def downRightTerr: SST

    /** Separator terrain for [[HSep]] down-left from the [[HVert]]. */
    def downLeftTerr: SST

    /** The magnitude of the [[HVUR]] up-right offset. */
    def magUR: Int

    /** The magnitude of the [[HVDn]] down offset. */
    def magDn: Int

    /** The magnitude of the [[HVUL]] up-left offset. */
    def magUL: Int

    def run(row: Int): Unit =
    { grid.hCenExistsIfDo(row + 1, c + 2){ corners.setCornerIn(row + 1, c + 2, 4, magUR) }
      grid.hCenExistsIfDo(row - 1, c){ corners.setCornerIn(row - 1, c, 0, magDn) }
      grid.hCenExistsIfDo(row + 1, c - 2){ corners.setCornerIn(row + 1, c - 2, 2, magUL) }
      sTerrs.setExists(grid, row + 1, c, upTerr)
      sTerrs.setExists(grid, row, c + 1, downRightTerr)
      sTerrs.setExists(grid, row, c - 1, downLeftTerr)
    }
  }
  
  /** Used for setting a vertex where 3 [[HSep]] terrains meet. Also sets the left most [[HSep]]. This trait is provided to model real world
   * geographic / terrain features and is probably superfluous for created worlds / terrain. */
  trait ThreeDownBase extends VertSetBase
  {/** Separator terrain for the [[HSep]] that is up-right from the [[HVert]]. */
    def upRightTerr: SST

    /** Separator terrain for the [[HSep]] that is down from the [[HVert]]. */
    def downTerr: SST

    /** Separator terrain for the [[HSep]] that is up-left from the [[HVert]]. */
    def upLeftTerr: SST

    /** The magnitude of the [[HVUp]] up offset. */
    def magUp: Int

    /** The magnitude of the [[HVDR]] down-right offset. */
    def magDR: Int

    /** The magnitude of the [[HVDL]] down-left offset. */
    def magDL: Int

    def run(row: Int): Unit =
    { grid.hCenExistsIfDo(row + 1, c) { corners.setCornerIn(row + 1, c, 3, magUp) }
      grid.hCenExistsIfDo(row - 1, c + 2) { corners.setCornerIn(row - 1, c + 2, 5, magDR) }
      grid.hCenExistsIfDo(row - 1, c - 2) { corners.setCornerIn(row - 1, c -2, 1, magDL) }
      sTerrs.setExists(grid, row, c + 1, upRightTerr)
      sTerrs.setExists(grid, row - 1, c, downTerr)
      sTerrs.setExists(grid, row, c - 1, upLeftTerr)
    }
  }

  /** This is for setting [[HSep]]s on the edge of grids that sit within the hex area of the tile on the neighbouring grid. */
  trait SetSepBase
  { def c: Int
    def terr: SST
    def run(row: Int): Unit = sTerrs.setExists(grid, row, c, terr)
  }

  /** Used for setting the corners of a vertex on the right edge of a grid. Sets the vertex to the left on both hex tiles. */
  trait BendLtOutBase
  { /** The c coordinate of the vertex. */
    def c: Int

    /** The magnitude of the offset. */
    def magnitude: Int

    /** The terrain of the left [[HSep]] of the junction as seen from the inside of the bend. */
    def leftTerr: SST

    /** The terrain of the right [[HSep]] of the junction as seen from the inside of the bend. */
    def rightTerr: SST

    def run(row: Int): Unit = if (HVert.rcISHigh(row, c))
    { corners.setCorner(row + 1, c - 2, 2, HVLt, magnitude)
      corners.setCorner(row - 1, c, 0, HVLt, magnitude)
      sTerrs.setExists(grid, row - 1, c + 1, leftTerr)
      sTerrs.setExists(grid, row + 1, c + 1, rightTerr)
    }
    else
    { corners.setCorner(row + 1, c, 3, HVLt, magnitude)
      corners.setCorner(row - 1, c - 2, 1, HVLt, magnitude)
      sTerrs.setExists(grid, row - 1, c + 1, leftTerr)
      sTerrs.setExists(grid, row + 1, c + 1, rightTerr)
    }
  }
}