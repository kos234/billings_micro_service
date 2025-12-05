export function formatNumbers(number){
    const n = Number(number);

    if (isNaN(n) || !isFinite(n)) {
        return String(number);
    }

    const [integerPart, decimalPart] = n.toString().split('.');

    const formattedInteger = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ' ');

    return decimalPart !== undefined
        ? `${formattedInteger}.${decimalPart}`
        : formattedInteger;
}