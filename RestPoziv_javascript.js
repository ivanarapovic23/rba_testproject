pm.test("Convert EUR to GBP", async () => {
  const startingCurrency = 'EUR';
  const convertedCurrency = 'GBP';
  const amountList = [100.00, 200.00, 300.00];

  await pm.sendRequest({
    url: 'https://www.rba.hr/alati/tecajni-kalkulator',
    method: 'GET'
  });

  const rateTypeDropdownMenu = await pm.page.$('#kurs');
  await rateTypeDropdownMenu.select('0');
  for (const amount of amountList) {
    const startingCurrencyInput = await pm.page.$('input[name="valuta1"]');
    await startingCurrencyInput.fill(startingCurrency);
    const convertedCurrencyInput = await pm.page.$('input[name="valuta2"]');
    await convertedCurrencyInput.fill(convertedCurrency);
    const amountInput = await pm.page.$('input[name="suma1"]');
    await amountInput.fill(String(amount));

    await pm.page.waitForTimeout(2000);

    const exchangeRate = await pm.page.$('#rateExch > font > font');
    const exchangeRateText = await exchangeRate.textContent();
    const finalAmount = await pm.page.$('#toHouseExch > font > font');
    const finalAmountText = await finalAmount.textContent();
    pm.expect(exchangeRateText).to.exist;
    pm.expect(finalAmountText).to.exist;
    console.log(`Tečaj preračunavanja iz ${startingCurrency} u ${convertedCurrency} je ${exchangeRateText} a konačni izračun za zadani iznos je ${finalAmountText}`);
  }
});

pm.test("Convert USD to EUR", async () => {
  const startingCurrency = 'USD';
  const convertedCurrency = 'EUR';
  const amountList = [250.00, 450.00, 600.00];
  await pm.sendRequest({
    url: 'https://www.rba.hr/alati/tecajni-kalkulator',
    method: 'GET'
  });

  const rateTypeDropdownMenu = await pm.page.$('#kurs');
  await rateTypeDropdownMenu.select('2');
  for (const amount of amountList) {
    const startingCurrencyInput = await pm.page.$('input[name="valuta1"]');
    await startingCurrencyInput.fill(startingCurrency);
    const convertedCurrencyInput = await pm.page.$('input[name="valuta2"]');
    await convertedCurrencyInput.fill(convertedCurrency);
    const amountInput = await pm.page.$('input[name="suma1"]');
    await amountInput.fill(String(amount));
    await pm.page.waitForTimeout(2000);

    const exchangeRate = await pm.page.$('#rateExch > font > font');
    const exchangeRateText = await exchangeRate.textContent();
    const finalAmount = await pm.page.$('#toHouseExch > font > font');
    const finalAmountText = await finalAmount.textContent();

    pm.expect(exchangeRateText).to.exist;
    pm.expect(finalAmountText).to.exist;

    console.log(`Tečaj preračunavanja iz ${startingCurrency} u ${convertedCurrency} je ${exchangeRateText} a konačni izračun za zadani iznos je ${finalAmountText}`);
  }
});
