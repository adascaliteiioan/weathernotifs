package com.ai.weathernotifications.util// Basic program that calculates a statement of a customer's charges at a car rental store.
//
// A customer can have multiple "com.ai.weathernotifications.util.Rental"s and a "com.ai.weathernotifications.util.Rental" has one "com.ai.weathernotifications.util.Car"
// As an ASCII class diagram:
//          com.ai.weathernotifications.util.Customer 1 ----> * com.ai.weathernotifications.util.Rental
//          com.ai.weathernotifications.util.Rental   * ----> 1 com.ai.weathernotifications.util.Car
//
// The charges depend on how long the car is rented and the type of the car (economy, muscle or supercar)
//
// The program also calculates frequent renter points.
//
//
// Refactor this class how you would see fit.
//
// The actual code is not that important, as much as its structure. You can even use "magic" functions (e.g. foo.sort()) if you want

enum class CarPriceCode {
    Economy,
    Supercar,
    Muscle
}

data class Car(
    val title: String,
    val priceCode: CarPriceCode
)

data class Rental(
    val car: Car,
    val daysRented: Int
)

class Customer(val name: String) {
    private var rentals = ArrayList<Rental>();

    fun addRental(arg: Rental) {
        rentals.add(arg);
    }

    fun billingStatement(): String {

        var totalAmount = 0.0
        var frequentRenterPoints = 0

        val result = StringBuilder("com.ai.weathernotifications.util.Rental Record for $name\n")

        rentals.forEach { each ->
            var amount = 0.0

            //determine amounts for each line
            when (each.car.priceCode) {
                CarPriceCode.Economy -> {
                    amount += economyStandardAmount
                    if (each.daysRented > economyStandardRentedDays) {
                        amount += (each.daysRented - economyStandardRentedDays).toDouble() * economyMultiplier
                    }
                }
                CarPriceCode.Supercar -> {
                    amount += (each.daysRented).toDouble() * supercarMultiplier
                }
                CarPriceCode.Muscle -> {
                    amount += muscleStandardAmount
                    if (each.daysRented > muscleStandardRentedDays) {
                        amount += ((each.daysRented).toDouble() - muscleStandardRentedDays) * muscleMultiplier
                    }
                }
            }

            // add frequent renter points
            frequentRenterPoints += 1
            // add bonus for a two day new release rental
            if ((each.car.priceCode == CarPriceCode.Supercar) && each.daysRented > 1) {
                frequentRenterPoints += 1
            }
            //show figures for this rental
            result.append("\t${each.car.title}\t$amount\n")
            totalAmount += amount;
        }

        //add footer lines
        result.append("Final rental payment owed $totalAmount\n")
        result.append("You received an additional $frequentRenterPoints frequent customer points")
        return result.toString()
    }

    companion object {
        private const val economyStandardAmount = 80
        private const val economyStandardRentedDays = 2
        private const val economyMultiplier = 30

        private const val supercarMultiplier = 200

        private const val muscleStandardAmount = 200
        private const val muscleStandardRentedDays = 3
        private const val muscleMultiplier = 50
    }
}


//val rental1 = com.ai.weathernotifications.util.Rental(com.ai.weathernotifications.util.Car("Mustang", com.ai.weathernotifications.util.Car.MUSCLE), 5)
//val rental2 = com.ai.weathernotifications.util.Rental(com.ai.weathernotifications.util.Car("Lambo", com.ai.weathernotifications.util.Car.SUPERCAR), 20)
//val customer = com.ai.weathernotifications.util.Customer("Liviu")
//customer.addRental(rental1)
//customer.addRental(rental2)



