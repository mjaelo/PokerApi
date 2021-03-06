export class CardMap {

  public cards: Array<{ id: number, symbol: string }> = [
    {id: 1, symbol: "2C"},
    {id: 2, symbol: "2D"},
    {id: 3, symbol: "2H"},
    {id: 4, symbol: "2S"},
    {id: 5, symbol: "3C"},
    {id: 6, symbol: "3D"},
    {id: 7, symbol: "3H"},
    {id: 8, symbol: "3S"},
    {id: 9, symbol: "4C"},
    {id: 10, symbol: "4D"},
    {id: 11, symbol: "4H"},
    {id: 12, symbol: "4S"},
    {id: 13, symbol: "5C"},
    {id: 14, symbol: "5D"},
    {id: 15, symbol: "5H"},
    {id: 16, symbol: "5S"},
    {id: 17, symbol: "6C"},
    {id: 18, symbol: "6D"},
    {id: 19, symbol: "6H"},
    {id: 20, symbol: "6S"},
    {id: 21, symbol: "7C"},
    {id: 22, symbol: "7D"},
    {id: 23, symbol: "7H"},
    {id: 24, symbol: "7S"},
    {id: 25, symbol: "8C"},
    {id: 26, symbol: "8D"},
    {id: 27, symbol: "8H"},
    {id: 28, symbol: "8S"},
    {id: 29, symbol: "9C"},
    {id: 30, symbol: "9D"},
    {id: 31, symbol: "9H"},
    {id: 32, symbol: "9S"},
    {id: 33, symbol: "10C"},
    {id: 34, symbol: "10D"},
    {id: 35, symbol: "10H"},
    {id: 36, symbol: "10S"},
    {id: 37, symbol: "JC"},
    {id: 38, symbol: "JD"},
    {id: 39, symbol: "JH"},
    {id: 40, symbol: "JS"},
    {id: 41, symbol: "QC"},
    {id: 42, symbol: "QD"},
    {id: 43, symbol: "QH"},
    {id: 44, symbol: "QS"},
    {id: 45, symbol: "KC"},
    {id: 46, symbol: "KD"},
    {id: 47, symbol: "KH"},
    {id: 48, symbol: "KS"},
    {id: 49, symbol: "AC"},
    {id: 50, symbol: "AD"},
    {id: 51, symbol: "AH"},
    {id: 52, symbol: "AS"},
    {id: 53, symbol: "blue_back"},
    {id: 54, symbol: "gray_back"},
    {id: 55, symbol: "green_back"},
    {id: 56, symbol: "red_back"},
    {id: 57, symbol: "yellow_back"},
  ];

  public mapToSym(id: number): string {
    for (const card of this.cards) {
      if (card.id === id) {
        return card.symbol;
      }
    }
    return "";
  }
}
