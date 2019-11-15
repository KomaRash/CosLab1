package sample

import scala.util.Random
import java.lang.Math.{pow, sqrt}

import scala.annotation.tailrec
object Kmeans {
  val K=2
  case class Point(square: Double, perimetr: Double, elongation: Double,comp:Double ) {




    def distanceTo(that: Point) = sqrt(pow(this.square - that.square, 2)
      + pow(this.perimetr - that.perimetr, 2)
      + pow(this.elongation - that.elongation, 2)
      + pow(this.comp - that.comp, 2))
    def sum(that: Point) = Point(this.square + that.square,this.perimetr + that.perimetr,  this.elongation + that.elongation,this.comp +that.comp)

    def divideBy(number: Int) = Point(this.square/number, this.perimetr/number, this.elongation/number,this.comp/number )

  }
  def createRandomCentroids(points: List[Point]): Map[Point, List[Point]] = {
    val randomIndices = collection.mutable.HashSet[Int]()
    val random = new Random()
    while (randomIndices.size < K) {
      randomIndices += random.nextInt(points.size)
    }

    points
      .zipWithIndex
      .filter({ case (_, index) => randomIndices.contains(index) })
      .map({ case (point, _) => (point, Nil) })
      .toMap
  }

    @tailrec
  def buildClusters(points: List[Point], prevClusters: Map[Point, List[Point]]): Map[Point, List[Point]] = {
    val nextClusters = points.map({ point =>
      val byDistanceToPoint = new Ordering[Point] {
        override def compare(p1: Point, p2: Point) = p1.distanceTo(point) compareTo p2.distanceTo(point)
      }

      (point, prevClusters.keys min byDistanceToPoint)
    }).groupBy({ case (_, centroid) => centroid })
      .map({ case (centroid, pointsToCentroids) =>
        val points = pointsToCentroids.map({ case (point, _) => point })
        (centroid, points)
      })

    if (prevClusters != nextClusters) {
      val nextClustersWithBetterCentroids = nextClusters.map({
        case (centroid, members) =>
          val (sum, count) = members.foldLeft((Point(0, 0, 0, 0), 0))({ case ((acc, c), curr) => (acc sum curr, c + 1) })
          (sum divideBy count, members)
      })

      buildClusters(points, nextClustersWithBetterCentroids)
    } else {
      prevClusters
    }
  }

}

