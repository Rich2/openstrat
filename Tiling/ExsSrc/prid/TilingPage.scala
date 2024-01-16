/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import ostrat.geom._, prid.phex._, pWeb._, Colour._

/** Html documentation page for Tiling Module. */
object TilingPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Tiling Module", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Tiling Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", list, HtmlH2("Game Terminology"), CoordSystem, terms2, turnRes)

  def list: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("The Tiling module contains"), coord, tileGeom, grid, sys, proj, terms, turn, exs)

  def coord: HtmlLi = HtmlLi("Hex and Square tile coordinate system, allowing the tile centres, the tile sides and the tile vertices to each have" --
    "their own unique 2 integer coordinates.")

  def tileGeom: HtmlLi = HtmlLi("Tile collections, tile paths and tile polygons and other tile structures, allowing their manipulation as tiles.")

  def grid: HtmlLi = HtmlLi("Tile grid classes that describe the tile grids but do not contain any data. For example an 8 by 8 square grid" +
    " would describe a chess board, but the class contains no data as to the position of the chess pieces, or even that it is being used for a" +
    "chess game.")

  def sys: HtmlLi = HtmlLi("Tile Grid system classes allowing for multiple hex grids to be manipulated the same as a single grid. This enables" +
    "the code in the EGrid module and other possible non Euclidean tile systems.")

  def proj: HtmlLi = HtmlLi("Projections. Projections allow the separation of the visual display of tile geometry from the applications and" +
    " objects within the grid systems.")

  def terms: HtmlLi = HtmlLi("Scenario and Game terminology,")

  def turn: HtmlLi = HtmlLi("Turn system allowing those grid systems to be used in, multi, simultaneous, homogenious segment simultaneous turn" +
    " games and productions.")

  def exs: HtmlLi = HtmlLi("Example games to demonstrate the use of the design principles and code of this module.")



  def terms2: HtmlOl = HtmlOl(HtmlLi("<b>ScenWorld</b> The universe of the scenario. Entities within the scenario universe have no knowledge of entities in" --
    "the GameWorld or the OutWorld."),
    HtmlLi("<b>GameWorld</b> the universe of the players in the game. The assignment of PlayerControl. The time dead lines and time credits for" --
      "submission of turn Directives. Rules on credits from InGame achievements or role-playering accreditation on future assignment of InWorld" --
      "entities. Rules on Player communication."),
    HtmlLi("<b>OutWorld</b> Anything outside of the Player universe. This can include servers, security, payement etc."),
    HtmlLi("<b>Player</b> A GameWorld entity. Maybe a human, an AI or a script."),
    HtmlLi("<b>PlayerControl</b> Assignment of an InWorld entity to a Player's control."),
    HtmlLi("<b>Directive</b> A player directive given by a player to an InWorld entity at the begining of a turn."),
    HtmlLi("<b>ControlHeirarchy</b> The precedence order of PlayerControl for Directives if multiple players are given control of an InGame entity."),
    HtmlLi("<b>Intention</b> Intention to make an effect by an InGame object. An Intention has a single SourceTile and a single TargetTile."),
    HtmlLi("<b>SourceTile</b> The location of an entity making an Intention."),
    HtmlLi("<b>TargetTile</b> The target an Intention. Maybe the sourceTile. For some Intentions it must be an adjacent tile for others it maybe a" --
      "non adjacent tile.")
  )

  def turnRes: HtmlSection = new HtmlSection
  {
    override def contents: RArr[XCon] = RArr(HtmlH2("Turn Resolution"), list)

    def list = HtmlOl(
      HtmlLi("Verify directives from Players are valid and under the PlayerControl"),
      HtmlLi("Resolve ControlHeirarchy"),
      HtmlLi("Map Directives to intentions. In more complex games some Directives maybe ignored or defered by InGame Objects."),
      HtmlLi("Aggregate Intentions."),
      HtmlLi("Add subsidary Intentions."),
      HtmlLi(RArr(HtmlUlWithLH.strs("Resolve Segment",
        "Accumulate. Aggregate the intentions in their TargetTiles.",
        "Ajudicate. Determine the outcome of the Intentions and processes.",
        "Consolidate. Create a new game state from the outcomes of the Intentions on the TargetTile. No Movement is implemented during this stage.",
        "Distribute. Distribute the movement effects of the resolution of the TargetTiles on the SourceTiles in the Consilidated Game State. Moving" --
          "entities from SourceTiles to TargetTiles when appropriate."
      ))),
      HtmlLi("Repeat Resolve Segement till the given number of segments has been repeated."),
      HtmlLi("Inform the players, human and AI, of the Turn resolution.")
    )
  }
}

object CoordSystem extends HtmlSection
{
  override def contents: RArr[XCon] = RArr(HtmlH2("Coordinate System"), p1, p2, p3, p4, svg1)

  val p1 = HtmlP("""So the primary focus of this project is regular tiling. Some strategy games use irregular tiling systems such as the old board
  | game Diplomacy the grand strategy Paradox Interactive game series Victoria, Europa Universals and Hearts of Iron, or the classic board game Risk,
  | which has become popular in its online form.Irregular tiles provides great flexibility when representing geography from the real world, allowing
  | the game designer to greatly exaggerate the features and boundaries, they want and to downplay and ignore the features and boundaries they
  | consider less important or distracting. The two dominant regular tiling systems are standard squares and hexs. Tranlge while useful as a graphics
  | primitive is not useful for strategy games. It is also possible to tile with squares where alternative rows or alternate columns are
  | offset.""".stripMargin)

  val p2 = HtmlP("""But from here on I'm only going to consider the standard square tiling and hex tiling. We'll focus on the hex grid system first
  | but a lot of what I say will apply to hex and square tile grid. Now with hex tiling we have a choice between nice continuous rows and nice
  | continuous columns. without too much ado I have chosen to keep rows over columns. We write in rows not colunms and this means on real world hex
  | maps our hex rows can follow lines of latitude, right around the earth if necessary, where as if you want to go north or south you have to zigzag.
  | We'll assign tiles some row and column integer coordinates. Straightforward for the squares, the only diference with the hexs is that when I move
  | right I increase by 2 column units. If I go up-right I go up 1 row and right 1 column. If I then go down-right I down 1 and right anther column
  | giving the same result of same row coordinate but column increased by 2. """.stripMargin)

  val p3 = HtmlP("""So things are still pretty simple right? A hex is either land orits water. A hex is either hilly or or its not. If we were
  | creating some kind of low level Dungeons and Dragons type game a square could be either be rock which you can not enter of the open space of a
  | room or a cavern which you can enter. Unfortunately this is not going to work at least for real world maps. So I have mapped Britain at a number
  | of scales including 80km hex tiles. At this scale mainland Britain occupies 15 hex rows from Cornwall to the north of Scotland. However the
  | English Channel, or La Manche at its narrowest point still does not occupy a full hex at this scale.""".stripMargin)

  def p4: HtmlP = HtmlP(
    """The hex tile coordinate system
      | not only allows each hex tile to be given its own unique coordinate, but also assigns a unique coordinate to each of the separating borders
      | between the hexs and to each of the vertices of the tiles.""".stripMargin)

  val grid: HGridRect = HGridRect(3, 6)
  val seps: LineSegHCArr = grid.sepLineSegHCs
  val sc = 60
  val seps2: LinesDraw = seps.map(_.lineSeg).draw(2).scale(sc)
  val cens: RArr[TextFixed] = grid.map { hc => hc.toPt2Reg.textAt(hc.strSemi, 10, Black) }
  val spt: RArr[GraphicSvgElem] = grid.sepsFlatMap{ sep => sep.toPt2Reg.scale(sc).textArrow(sep.strSemi, sep.anglePerpRt, 25, Blue, 10) }
  val vts = grid.vertsFlatMap{ hv => hv.toPt2Reg.scale(sc).textArrow(hv.strSemi, hv.angleOppLeft, 25, Green, 10) }
  val stuff = (cens).scale(sc)

  val svg1: HtmlSvg = HtmlSvg.autoHorrVert(100, 40, seps2 %: stuff ++ spt ++ vts, RArr(CentreBlockAtt))
}