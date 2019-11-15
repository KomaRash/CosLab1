package sample

import org.opencv.core.Size

object ListFigure {
var listFigure: List[Figure] =List[Figure]()
  def findFigure(mat:Array[Array[Byte]],size:Size): List[Figure] =
  {
    for (y <- 0 until size.height.toInt; x <- 0 until size.width.toInt)
    mat(y)(x) match {
      case 0 =>

    case col =>
        listFigure.find((f:Figure)=> f.color == col ) match {
          case None =>
            listFigure =listFigure++ List(new Figure(col).add(y,x))
          case Some(f) =>
            f.add((y,x))
        }
    }
    listFigure.map(_.kmeans())
    }
}
