/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package ppart
import geom.*

/** A Chemical compound. */
trait Molecule
{ val atoms: RArr[Atom]
  def atomInd(atom: Atom): Int = atoms.indexOf(atom)
  def atomPosns: PtPm2Arr
  def posnAtoms: PtPm2PairArr[Atom] = PtPm2PairArr(atomPosns, atoms)
  val bonds: RPairArr[Atom, Atom]

  def bondSegs: LineSegPm2Arr = bonds.map { bond =>
    val i1: PtPm2 = atomPosns(atomInd(bond.a1))
    val i2: PtPm2 = atomPosns(atomInd(bond.a2))
    LineSegPm2(i1, i2)
  }

  def circles: RArr[CircleLen2Compound] = posnAtoms.pairMap{ (pt, atom) => CircleLen2(atom.radius / 2, pt).fillDraw(atom.colour, atom.contrastBW) }
  def lines = bondSegs.draw()
  def linesCircles: RArr[GraphicLen2Elem] = lines ++ circles
}

object Dihydrogen extends Molecule
{
  val aH1 = HAtom()
  val aH2 = HAtom()
  override val atoms: RArr[Atom] = RArr(aH1, aH2)

  override def atomPosns: PtPm2Arr = PtPm2Arr.fromDbls(-37,0 ,37,0)
  override val bonds: RPairArr[Atom, Atom] = RPairArr((aH1, aH2))
}

object Water extends Molecule
{ val aH1 = HAtom()
  val aO1 = OAtom()
  val aH2 = HAtom()
  val bondsAngle = 104.52.degsVec
  val bondLen = 95.72.picometres
  private val bondRem: AngleVec = (180.degsVec - bondsAngle) / 2
  override val atoms: RArr[Atom] = RArr(aH1, aO1, aH2)
  val a01Posn = PtPm2.origin
  override val atomPosns: PtPm2Arr = PtPm2Arr(a01Posn.angleTo(-180.degs + bondRem, bondLen), a01Posn, a01Posn.angleTo(0.degs - bondRem, bondLen))
  override val bonds: RPairArr[Atom, Atom] = RPairArr((aH1, aO1), (aO1, aH2))
}

object CO2Mc extends Molecule
{ val aO1 = OAtom()
  val aC1 = CAtom()
  val aO2 = OAtom()
  override val atoms: RArr[Atom] = RArr(aO1, aC1, aO2)
  val bondLenNum = 116.3
  override def atomPosns: PtPm2Arr = PtPm2Arr.fromDbls(-bondLenNum,0 ,0,0, bondLenNum, 0)
  override val bonds: RPairArr[Atom, Atom] = RPairArr((aC1, aO1), (aC1, aO2))
}