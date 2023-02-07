<template >

  <div id="app" >
    <h5>Sign your attached picture below</h5>
    <vueSignature ref="signature" :sigOption="option"></vueSignature>
    <button @click="save">Save</button>
    <button @click="clear">Clear Sign</button>
    <button @click="undo">Undo</button>
  </div>

</template>

<script>
import vueSignature from "vue-signature"
export default {
  name: "app",
  components:{
    vueSignature
  },
  data() {
    return {
      option:{
        penColor:"Navy",
        backgroundColor:"MintCream"
      }
    };
  },
  methods:{
    save(){
      let _this = this;
      let png = _this.$refs.signature.save()
      let jpeg = _this.$refs.signature.save('image/jpeg')
      let svg = _this.$refs.signature.save('image/svg+xml');
      console.log(png);
      console.log(jpeg)
      console.log(svg)
    },
    clear(){
      let _this = this;
      _this.$refs.signature.clear();
    },
    undo(){
      let _this = this;
      _this.$refs.signature.undo();
    },
    fromDataURL(url){
      let _this = this;
      _this.$refs.signature.fromDataURL("base64");
    },
    handleDisabled(){
      let _this = this;
      _this.disabled  = !_this.disabled
    }
  }
};
</script>

<style lang="scss">

    h5 {
      margin: 15px;
    }

    .canvas {
      border: 2px solid rgb(178,178,178);
      border-radius: 5px;
      width: 60%;
      height: 40%;
    }

    button{
      margin: 8px;
      border-radius: 5px;
    }

</style>
