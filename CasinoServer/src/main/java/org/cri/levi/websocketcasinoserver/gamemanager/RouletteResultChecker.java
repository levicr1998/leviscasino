package org.cri.levi.websocketcasinoserver.gamemanager;

import org.cri.levi.websocketcasinoserver.endpoint.CasinoWebsocketService;
import org.cri.levi.websocketcasinoshared.models.gamemodels.RouletteBetOption;

public class RouletteResultChecker {

    public void checkNumber(int resultRouletteNumber, int betRouletteNumber, int bet, int playerID) {
        if (resultRouletteNumber == betRouletteNumber) {
            CasinoWebsocketService.getInstance().sendProfit(playerID, calculateProfit(bet, 36));
        }
    }

    public void checkOtherOption(int resultRouletteNumber, RouletteBetOption rouletteBetOption, int bet, int playerID) {
        switch (rouletteBetOption) {
            case ODD:
                if (checkOdd(resultRouletteNumber)) {
                    CasinoWebsocketService.getInstance().sendProfit(playerID, calculateProfit(bet, 2));
                }
                break;
            case EVEN:
                if (checkEven(resultRouletteNumber)) {
                    CasinoWebsocketService.getInstance().sendProfit(playerID, calculateProfit(bet, 2));
                }
                break;
            case RED:
                if (checkRed(resultRouletteNumber)) {
                    CasinoWebsocketService.getInstance().sendProfit(playerID, calculateProfit(bet, 2));
                }
                break;
            case BLACK:
                if (checkBlack(resultRouletteNumber)) {
                    CasinoWebsocketService.getInstance().sendProfit(playerID, calculateProfit(bet, 2));
                }
                break;
            case FIRST18:
                if (checkFirstEighteen(resultRouletteNumber)) {
                    CasinoWebsocketService.getInstance().sendProfit(playerID, calculateProfit(bet, 2));
                }
                break;
            case SECOND18:
                if (checkSecondEighteen(resultRouletteNumber)) {
                    CasinoWebsocketService.getInstance().sendProfit(playerID, calculateProfit(bet, 2));
                }
                break;
            case FIRSTTWELVE:
                if (checkFirstTwelve(resultRouletteNumber)) {
                    CasinoWebsocketService.getInstance().sendProfit(playerID, calculateProfit(bet, 3));
                }
                break;
            case SECONDTWELVE:
                if (checkSecondTwelve(resultRouletteNumber)) {
                    CasinoWebsocketService.getInstance().sendProfit(playerID, calculateProfit(bet, 3));
                }
                break;
            case TIRTHTWELVE:
                if (checkThirthTwelve(resultRouletteNumber)) {
                    CasinoWebsocketService.getInstance().sendProfit(playerID, calculateProfit(bet, 3));
                }
                break;
                default:
                    break;
        }


    }


    public boolean checkBlack(int number) {
        if (number > 0 && number <= 9) {
            if (number % 2 == 0) {
                return true;
            }
        } else if (number > 9 && number <= 18) {
            if (number % 2 != 0 || number == 10) {
                return true;
            }
        } else if (number > 18 && number <= 27) {
            if (number % 2 == 0) {
                return true;
            }
        } else if (number > 27) {
            if (number % 2 != 0 || number == 28) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRed(int number) {
        if (number > 0 && number <= 9) {
            if (number % 2 != 0) {
                return true;
            }
        } else if (number > 9 && number <= 18) {
            if (number % 2 == 0 && number != 10) {
                return true;
            }
        } else if (number > 18 && number <= 27) {
            if (number % 2 != 0) {
                return true;
            }
        } else if (number > 27) {
            if (number % 2 == 0 && number != 28) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEven(int resultRouletteNumber) {
        if (resultRouletteNumber % 2 == 0) {
            return true;
        }
        return false;
    }

    public boolean checkOdd(int resultRouletteNumber) {
        if (resultRouletteNumber % 2 != 0) {
            return true;
        }
        return false;
    }

    public boolean checkFirstTwelve(int resultRouletteNumber) {
        if (resultRouletteNumber >= 1 && resultRouletteNumber <= 12) {
            return true;
        }
        return false;
    }

    public boolean checkSecondTwelve(int resultRouletteNumber) {
        if (resultRouletteNumber >= 13 && resultRouletteNumber <= 24) {
            return true;
        }
        return false;

    }

    public boolean checkThirthTwelve(int resultRouletteNumber) {
        if (resultRouletteNumber >= 25 && resultRouletteNumber <= 36) {
            return true;
        }
        return false;

    }

    public boolean checkFirstEighteen(int resultRouletteNumber) {
        if (resultRouletteNumber >= 1 && resultRouletteNumber <= 18) {
            return true;
        }
        return false;

    }

    public boolean checkSecondEighteen(int resultRouletteNumber) {
        if (resultRouletteNumber >= 19 && resultRouletteNumber <= 36) {
            return true;
        }
        return false;

    }

    public int calculateProfit(int bet, int multiplier) {
        int profit = bet * multiplier;
        return profit;
    }
}
